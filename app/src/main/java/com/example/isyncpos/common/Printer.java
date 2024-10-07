package com.example.isyncpos.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.EscPosPrinterCommands;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.connection.usb.UsbConnection;
import com.dantsu.escposprinter.connection.usb.UsbPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.entity.CashFund;
import com.example.isyncpos.entity.CashFundDenomination;
import com.example.isyncpos.entity.CutOff;
import com.example.isyncpos.entity.CutOffDepartments;
import com.example.isyncpos.entity.CutOffDiscounts;
import com.example.isyncpos.entity.CutOffPayments;
import com.example.isyncpos.entity.Devices;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.entity.EndOfDay;
import com.example.isyncpos.entity.EndOfDayDepartments;
import com.example.isyncpos.entity.EndOfDayDiscounts;
import com.example.isyncpos.entity.EndOfDayPayments;
import com.example.isyncpos.entity.OfficialReceiptInformation;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.entity.Payments;
import com.example.isyncpos.entity.PrinterSetup;
import com.example.isyncpos.entity.PrinterSetupDevices;
import com.example.isyncpos.entity.Safekeeping;
import com.example.isyncpos.entity.SafekeepingDenomination;
import com.example.isyncpos.entity.SpotAudit;
import com.example.isyncpos.entity.SpotAuditDenomination;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.viewmodel.DevicesViewModel;
import com.example.isyncpos.viewmodel.DiscountOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.OfficialReceiptInformationViewModel;
import com.example.isyncpos.viewmodel.PaymentOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Printer {

    private static Printer instance;
    private DevicesViewModel devicesViewModel;
    private PaymentOtherInformationsViewModel paymentOtherInformationsViewModel;
    private OfficialReceiptInformationViewModel officialReceiptInformationViewModel;
    private DiscountOtherInformationsViewModel discountOtherInformationsViewModel;
    private PrinterSetupViewModel printerSetupViewModel;
    private int printerDPI = 203;
    private int dotsFeedPaper = 100;

    public synchronized static Printer getInstance(){
        if(instance == null){
            instance = new Printer();
        }
        return instance;
    }

    public void setDevicesViewModel(DevicesViewModel devicesViewModel){
        this.devicesViewModel = devicesViewModel;
    }

    public void setPaymentOtherInformationsViewModel(PaymentOtherInformationsViewModel paymentOtherInformationsViewModel){
        this.paymentOtherInformationsViewModel = paymentOtherInformationsViewModel;
    }

    public void setOfficialReceiptInformationViewModel(OfficialReceiptInformationViewModel officialReceiptInformationViewModel) {
        this.officialReceiptInformationViewModel = officialReceiptInformationViewModel;
    }

    public void setDiscountOtherInformationsViewModel(DiscountOtherInformationsViewModel discountOtherInformationsViewModel) {
        this.discountOtherInformationsViewModel = discountOtherInformationsViewModel;
    }

    public void setPrinterSetupViewModel(PrinterSetupViewModel printerSetupViewModel) {
        this.printerSetupViewModel = printerSetupViewModel;
    }

    public void print(Activity activity, List<PrinterSetupDevices> printerSetupDevices, String content){
        if(printerSetupDevices.size() != 0){
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            for(PrinterSetupDevices item : printerSetupDevices) {
                try {
                    PrinterSetup printerSetup = printerSetupViewModel.fetchPrinterSetup(item.getPrinterSetupId());
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if(devicesViewModel != null){
                                    Devices devices = devicesViewModel.fetchDeviceID(item.getDeviceId());
                                    if (devices != null){
                                        if(devices.getType().equals("USB")){
                                            printUSB(activity, devices.getDevice(), content, printerSetup, false);
                                        } else if (devices.getType().equals("BLUETOOTH")) {
                                            printBluetooth(activity, devices.getDevice(), content, printerSetup, false);
                                        }
                                    }
                                }
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void printUSB(Activity activity, String deviceName, String content,@Nullable PrinterSetup printerSetup, boolean openDrawer){
        UsbManager usbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        UsbDevice usbDevice = (UsbDevice) usbManager.getDeviceList().get(deviceName);
        DeviceConnection deviceConnection = new UsbConnection(usbManager, usbDevice);
        UsbConnection usbConnection = UsbPrintersConnections.selectFirstConnected(activity);

        if(deviceConnection.isConnected() || usbConnection != null){
            try {
                EscPosPrinter escPosPrinter = new EscPosPrinter(deviceConnection.isConnected() ? deviceConnection : usbConnection, printerDPI, POSApplication.getInstance().getPrinterWidthMM(), POSApplication.getInstance().getPrinterNBRCharacterLine());
                if(printerSetup != null){
                    int printerCounter = 0;
                    while (printerCounter < printerSetup.getPrintCount()){
                        if(printerSetup.getCashDrawer() == 1){
                            escPosPrinter.printFormattedTextAndOpenCashBox(content, dotsFeedPaper);
                        }
                        else{
                            escPosPrinter.printFormattedTextAndCut(content);
                        }
                        printerCounter ++;
                    }
                }
                else{
                    if(openDrawer){
                        escPosPrinter.printFormattedTextAndOpenCashBox(content, dotsFeedPaper);
                    }
                    else{
                        escPosPrinter.printFormattedTextAndCut(content);
                    }
                }
            } catch (EscPosConnectionException | EscPosParserException |
                     EscPosEncodingException | EscPosBarcodeException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean printUSBCashDrawer(Activity activity, String content,@Nullable UserAuthentication authorizeUser){
        UsbConnection usbConnection = UsbPrintersConnections.selectFirstConnected(activity);
        if(usbConnection != null){
            try {
                //Save Audit Trail
                AuditTrail.getInstance().save(
                        POSApplication.getInstance().getUserAuthentication().getId(),
                        POSApplication.getInstance().getUserAuthentication().getUsername(),
                        0,
                        "OPEN CASH DRAWER",
                        getCashDrawerDescription(authorizeUser),
                        authorizeUser != null ? authorizeUser.getId() : 0,
                        authorizeUser != null ? authorizeUser.getName() : "",
                        0,
                        0
                );
                EscPosPrinter escPosPrinter = new EscPosPrinter(usbConnection, printerDPI, POSApplication.getInstance().getPrinterWidthMM(), POSApplication.getInstance().getPrinterNBRCharacterLine());
                escPosPrinter.printFormattedTextAndOpenCashBox(content, dotsFeedPaper);
                return true;
            } catch (EscPosConnectionException | EscPosEncodingException | EscPosBarcodeException |
                     EscPosParserException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            return false;
        }
    }

    @SuppressLint("MissingPermission")
    private void printBluetooth(Activity activity, String deviceName, String content,@Nullable PrinterSetup printerSetup, boolean openDrawer){
        BluetoothConnection[] bluetoothDevicesList = POSApplication.getInstance().getBluetoothConnection();
        for(BluetoothConnection item : bluetoothDevicesList){
            if(item.getDevice().getName().equals(deviceName)){
                DeviceConnection deviceConnection = item;
                try {
                    EscPosPrinter escPosPrinter = new EscPosPrinter(deviceConnection.isConnected() ? deviceConnection : BluetoothPrintersConnections.selectFirstPaired(), printerDPI, POSApplication.getInstance().getPrinterWidthMM(), POSApplication.getInstance().getPrinterNBRCharacterLine());
                    if(printerSetup != null){
                        int printerCounter = 0;
                        while (printerCounter < printerSetup.getPrintCount()){
                            if(printerSetup.getCashDrawer() == 1){
                                escPosPrinter.printFormattedTextAndOpenCashBox(content, dotsFeedPaper);
                            }
                            else{
                                escPosPrinter.printFormattedTextAndCut(content);
                            }
                            printerCounter ++;
                        }
                    }
                    else{
                        if(openDrawer){
                            escPosPrinter.printFormattedTextAndOpenCashBox(content, dotsFeedPaper);
                        }
                        else{
                            escPosPrinter.printFormattedTextAndCut(content);
                        }
                    }
                } catch (EscPosConnectionException e) {
                    item.disconnect();
                    throw new RuntimeException(e);
                } catch (EscPosEncodingException | EscPosBarcodeException | EscPosParserException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private boolean printBluetoothCashDrawer(Activity activity, String content, @Nullable UserAuthentication authorizeUser){
        try {
            BluetoothConnection selectedDevice;
            BluetoothConnection[] bluetoothDevicesList = POSApplication.getInstance().getBluetoothConnection();
            if(bluetoothDevicesList.length != 0){
                selectedDevice = bluetoothDevicesList[0];
                if(!selectedDevice.isConnected()){
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LoadingDialog.getInstance().startLoadingDialog(activity, "Connecting bluetooth device please wait..");
                                }
                            });

                            try {
                                EscPosPrinter escPosPrinter = new EscPosPrinter(selectedDevice, printerDPI, POSApplication.getInstance().getPrinterWidthMM(), POSApplication.getInstance().getPrinterNBRCharacterLine());
                                if(selectedDevice.isConnected()){
                                    //Save Audit Trail
                                    AuditTrail.getInstance().save(
                                            POSApplication.getInstance().getUserAuthentication().getId(),
                                            POSApplication.getInstance().getUserAuthentication().getUsername(),
                                            0,
                                            "OPEN CASH DRAWER",
                                            getCashDrawerDescription(authorizeUser),
                                            authorizeUser != null ? authorizeUser.getId() : 0,
                                            authorizeUser != null ? authorizeUser.getName() : "",
                                            0,
                                            0
                                    );
                                    escPosPrinter.printFormattedTextAndOpenCashBox(content, dotsFeedPaper);
                                }
                            } catch (EscPosEncodingException | EscPosBarcodeException | EscPosParserException e) {
                                throw new RuntimeException(e);
                            } catch (EscPosConnectionException e) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity, "Cannot to connect to bluetooth printer.", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LoadingDialog.getInstance().closeLoadingDialog();
                                }
                            });

                        }
                    });
                    return true;
                }
                else {
                    //Save Audit Trail
                    AuditTrail.getInstance().save(
                            POSApplication.getInstance().getUserAuthentication().getId(),
                            POSApplication.getInstance().getUserAuthentication().getUsername(),
                            0,
                            "OPEN CASH DRAWER",
                            getCashDrawerDescription(authorizeUser),
                            authorizeUser != null ? authorizeUser.getId() : 0,
                            authorizeUser != null ? authorizeUser.getName() : "",
                            0,
                            0
                    );
                    EscPosPrinter escPosPrinter = new EscPosPrinter(selectedDevice, printerDPI, POSApplication.getInstance().getPrinterWidthMM(), POSApplication.getInstance().getPrinterNBRCharacterLine());
                    escPosPrinter.printFormattedTextAndOpenCashBox(content, dotsFeedPaper);
                    return true;
                }
            }
            else{
                return false;
            }
        } catch (EscPosConnectionException | EscPosEncodingException | EscPosBarcodeException |
                 EscPosParserException e) {
            throw new RuntimeException(e);
        }
    }

    public void testPrint(Activity activity, String deviceName, String type){
        String content = "[C]<u><font size='big'>PRINTER TEST " + deviceName + " " + type +"</font></u>\n" +
                "[C]================================\n" +
                "[C]HELLO WORLD FROM " + BuildConfig.POS_PROVIDER_NAME + "\n" +
                "[C]================================\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                if(type.equals("USB")){
                    printUSB(activity, deviceName, content, null, false);
                } else if (type.equals("BLUETOOTH")) {
                    printBluetooth(activity, deviceName, content, null, false);
                }

            }
        });
    }

    public void openCashDrawer(Activity activity,@Nullable UserAuthentication authorizeUser){
        String authorizeBy = openCashDrawerAuthorizeBy(authorizeUser);
        String content = "[C]<u><font size='big'>OPEN CASH DRAWER</font></u>\n" +
                "[C]================================\n" +
                "[C]Cashier: " + POSApplication.getInstance().getUserAuthentication().getName() + "\n" +
                "[C]Authorize By: " + authorizeBy + "\n" +
                "[C]================================\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if(!printUSBCashDrawer(activity, content, authorizeUser)){
                    printBluetoothCashDrawer(activity, content, authorizeUser);
                }
            }
        });
    }

    private String openCashDrawerAuthorizeBy(@Nullable UserAuthentication authorizeUser){
        return authorizeUser != null ? authorizeUser.getName() : "";
    }

    private String getCashDrawerDescription(@Nullable UserAuthentication authorizeUser) {
        String description = "";
        if(authorizeUser != null){
            description = "Open cash drawer cashier is " + POSApplication.getInstance().getUserAuthentication().getName() + " authorize by " + authorizeUser.getName() + ".";
        }
        else{
            description = "Open cash drawer cashier is " + POSApplication.getInstance().getUserAuthentication().getName() + ".";
        }
        return description;
    }

    public String takeOrderReciept(Transactions transactions){
        String content = "[C]<font size='big'>TAKE ORDER</font>\n" +
                "[C]------------------------------------------------\n" +
                "[L]<b>DEVICE #: </b>[R]"+ transactions.getTakeOrderId() +"\n" +
                "[L]<b>Customer Name: </b>[R]"+ transactions.getCustomerName() +"\n" +
                "[L]<b>CONTROL #: </b>[R]"+ transactions.getControlNumber() +"\n" +
                "[L]\n" +
                "[C]<qrcode size='25'>"+ transactions.getControlNumber() +"</qrcode>\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        return content;
    }

    public String resumeTransaction(Transactions transactions){
        String content = "[C]<u><font size='big'>RESUME TRANSACTION</font></u>\n" +
                "[C]================================\n" +
                "[C]<b>CONTROL #: </b>[R]"+ transactions.getControlNumber() +"\n" +
                "[L]\n" +
                "[C]<qrcode size='25'>"+ transactions.getControlNumber() +"</qrcode>\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        return content;
    }

    public String backoutTransaction(Transactions transactions, List<Orders> orders){
        Dates date = new Dates();
        Generate generate = new Generate();
        String ordersContent = "";
        String unitNumber = "";
        String soldToContent = "";
        for(Orders item : orders){
            String vatValue = item.getVatableSales() != 0 ? "V " : item.getVatExemptSales() != 0 ? "E " : "";
            ordersContent += "[L]"+ item.getQty() +"   "+ item.getName() + " @ " + generate.toTwoDecimalWithComma(item.getAmount())  + "[R]"+ generate.toTwoDecimalWithComma(item.getTotal()) + " " + vatValue +"\n";
            ordersContent += "\n";
        }
        //Unit Number
        if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
            unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + "/" + POSApplication.getInstance().getBranch().getFloorNumber() + " ";
        }
        else{
            if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() == null){
                unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + " ";
            }
            else if(POSApplication.getInstance().getBranch().getUnitNumber() == null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
                unitNumber = POSApplication.getInstance().getBranch().getFloorNumber() + " ";
            }
        }
        //Sold To Checking
        try {
            OfficialReceiptInformation officialReceiptInformation = officialReceiptInformationViewModel.fetchByTransactionId(transactions.getId());
            if(officialReceiptInformation != null){
                soldToContent = "[L]SOLD TO\n" +
                        "[L]NAME: [R]"+ officialReceiptInformation.getName() +"\n" +
                        "[L]ADDRESS: [R]"+ officialReceiptInformation.getAddress() +"\n" +
                        "[L]TIN#: [R]"+ officialReceiptInformation.getTin() +"\n" +
                        "[L]BUSINESS STYLE: [R]"+ officialReceiptInformation.getBusinessStyle() +"\n";
            }
            else{
                soldToContent = "[L]SOLD TO\n" +
                        "[L]NAME: ________________________________________\n" +
                        "[L]ADDRESS: _____________________________________\n" +
                        "[L]TIN#: ________________________________________\n" +
                        "[L]BUSINESS STYLE: ______________________________\n";
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Set VatContent
        double vatableSales = transactions.getNetSales() < 0 ? 0 : transactions.getVatableSales();
        double vatAmount = transactions.getNetSales() < 0 ? 0 : transactions.getVatAmount();
        String content = "[C]"+ POSApplication.getInstance().getCompany().getTradeName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getName() +"\n" +
                "[L]\n" +
                "[C]"+ POSApplication.getInstance().getMachineDetails().getReceiptHeader() +"\n" +
                "[C]"+ unitNumber + POSApplication.getInstance().getBranch().getStreet() + " " + POSApplication.getInstance().getBranch().getBarangayName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getCityName() + " " + POSApplication.getInstance().getBranch().getProvinceName() + "\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getRegionName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getPhoneNumber() +"\n" +
                "[C]VAT REG TIN: "+ POSApplication.getInstance().getMachineDetails().getTinNumber() +"\n" +
                "[C]SERIAL NO: "+ POSApplication.getInstance().getMachineDetails().getSerialNumber() +"\n" +
                "[C]MIN NO: "+ POSApplication.getInstance().getMachineDetails().getMinNumber() +"\n" +
                "[C]MACHINE NO: "+ POSApplication.getInstance().getMachineDetails().getMachineNumber() + "\n" +
                "[L]\n" +
                "[C]<b>*****BACKOUT*****</b>\n" +
                "[L]\n" +
                "[L]Control No:[R]"+ transactions.getControlNumber() + "\n" +
                "[L]DATE:[R]"+ transactions.getTreg() + "\n" +
                "[L]Cashier:[R]"+ transactions.getCashierName() + "\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[L]QTY   DESCRIPTION[R]AMOUNT\n" +
                "[C]------------------------------------------------\n" +
                "" + ordersContent + "" +
                "[L]\n" +
                "[L]Total Qty\n" +
                "[L]"+ generate.toTwoDecimalWithComma(transactions.getTotalQuantity()) +"\n" +
                "[L]\n" +
                "[L]Less VAT 12%[R]"+ generate.toTwoDecimalWithComma(transactions.getVatExpense()) +"\n" +
                "[L]\n" +
                "[L]VATABLE SALES[R]"+ generate.toTwoDecimalWithComma(vatableSales) +"\n" +
                "[L]VAT AMOUNT[R]"+ generate.toTwoDecimalWithComma(vatAmount) +"\n" +
                "[L]VAT-EXEMPT SALES[R]"+ generate.toTwoDecimalWithComma(transactions.getVatExemptSales()) +"\n" +
                "[L]ZERO-RATED SALES[R]"+ generate.toTwoDecimalWithComma(transactions.getTotalZeroRatedAmount()) +"\n" +
                "[L]\n" +
                "[L]SUB TOTAL[R]"+ generate.toTwoDecimalWithComma(transactions.getGrossSales()) +"\n" +
                "[L]AMOUNT DUE[R]"+ generate.toTwoDecimalWithComma(transactions.getNetSales()) +"\n" +
                "[L]TENDERED[R]0.00\n" +
                "[L]CHANGE[R]0.00\n" +
                "[L]\n" +
                "[L]PAYMENT TYPE\n" +
                "[L]\n" +
                "" + soldToContent + "" +
                "[L]\n" +
                "[L]REMARKS[R]"+ transactions.getRemarks() +"\n" +
                "[L]\n" +
                "[L]POS PROVIDER: "+ BuildConfig.POS_PROVIDER_NAME +"\n" +
                "[L]ADDRESS: "+ BuildConfig.POS_PROVIDER_ADDRESS +"\n" +
                "[L]VAT REG TIN: "+ BuildConfig.POS_PROVIDER_TIN +"\n" +
                "[L]ACCREDITED NO: "+ BuildConfig.POS_PROVIDER_ACCREDITATION +"\n" +
                "[L]PTU NO: "+ BuildConfig.POS_PROVIDER_PTU +"\n" +
                "[L]DATE ISSUED: "+ BuildConfig.POS_PROVIDER_DATE_ISSUED +"\n" +
                "[L]\n" +
                //"[C]THIS RECEIPT SHALL BE VALID FOR\n" +
                //"[C]FIVE("+ BuildConfig.POS_OR_VALID_YEAR +") YEARS FROM THE DATE OF\n" +
                //"[C]THE PERMIT TO USE\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        return content;
    }

    public String officialReceipt(Transactions transactions, List<Orders> orders, List<Payments> payments, List<Discounts> discounts){
        Dates date = new Dates();
        Generate generate = new Generate();
        Functions functions = new Functions();
        String ordersContent = "";
        String unitNumber = "";
        String paymentContent = "";
        String discountContent = "";
        String discountOtherContent = "";
        String returnContent = "";
        String soldToContent = "";
        String remarks = "";
        String label = "";
        String referenceNumber = "";
        //Order List
        for(Orders item : orders){
            String vatValue = item.getVatableSales() != 0 ? "V " : item.getVatExemptSales() != 0 ? "E " : "";
            ordersContent += "[L]"+ item.getQty() +"   "+ item.getName() + " @ " + generate.toTwoDecimalWithComma(item.getAmount())  + "[R]"+ generate.toTwoDecimalWithComma(item.getTotal()) + " " + vatValue +"\n";
            ordersContent += "\n";
            if(item.getSerialNumber() != null){
                if(!item.getSerialNumber().equals("")) ordersContent += "[L]S/N: " + item.getSerialNumber() + "\n";
            }
        }
        //Payment List
        paymentContent = "[L]PAYMENT TYPE\n";
        paymentContent += functions.filterByPaymentTypes(payments, paymentOtherInformationsViewModel);
        if(discounts.size() != 0){
            for(Discounts item : discounts){
                if(item.getIsZeroRated() != 1){
                    discountContent += "[L]" + item.getDiscountName() + "[R]" + generate.toTwoDecimalWithComma(item.getDiscountAmount()) + "\n";
                    //This will set the other information of the discount like the name and address.
                    try {
                        List<DiscountOtherInformations> discountOtherInformations = discountOtherInformationsViewModel.fetchByTransactionDiscountId(item.getId());
                        if(discountOtherInformations.size() != 0){
                            discountOtherContent += "[L]" + item.getDiscountName().toUpperCase() + "\n";
                            for(DiscountOtherInformations others : discountOtherInformations){
                                discountOtherContent += "[L]" + others.getName() + " " + others.getValue() + "\n";
                            }
                            discountOtherContent += "\n";
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        //Unit Number
        if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
            unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + "/" + POSApplication.getInstance().getBranch().getFloorNumber() + " ";
        }
        else{
            if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() == null){
                unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + " ";
            }
            else if(POSApplication.getInstance().getBranch().getUnitNumber() == null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
                unitNumber = POSApplication.getInstance().getBranch().getFloorNumber() + " ";
            }
        }
        if(transactions.getIsReturn() == 1){
            returnContent += "[C]THIS DOCUMENT IS NOT VALID\n";
            returnContent += "[C]FOR CLAIMING INPUT TAXES\n";
        }
        //Sold To Checking
        try {
            OfficialReceiptInformation officialReceiptInformation = officialReceiptInformationViewModel.fetchByTransactionId(transactions.getId());
            if(officialReceiptInformation != null){
                soldToContent = "[L]SOLD TO\n" +
                "[L]NAME: [R]"+ officialReceiptInformation.getName() +"\n" +
                "[L]ADDRESS: [R]"+ officialReceiptInformation.getAddress() +"\n" +
                "[L]TIN#: [R]"+ officialReceiptInformation.getTin() +"\n" +
                "[L]BUSINESS STYLE: [R]"+ officialReceiptInformation.getBusinessStyle() +"\n";
            }
            else{
                soldToContent = "[L]SOLD TO\n" +
                "[L]NAME: ________________________________________\n" +
                "[L]ADDRESS: _____________________________________\n" +
                "[L]TIN#: ________________________________________\n" +
                "[L]BUSINESS STYLE: ______________________________\n";
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Set Remarks
        if(transactions.getRemarks() != null){
            remarks = transactions.getRemarks();
        }
        //Check For Header
        if(transactions.getIsAccountReceivable() == 1){
            if(transactions.getReceiptNumber().isEmpty()){
                label = "*****ACCOUNT RECEIVABLE*****";
            }
        }
        //Check For Reference Number To Print
        if(transactions.getReceiptNumber().isEmpty()){
            referenceNumber = "[L]Control No:[R]"+ transactions.getControlNumber() + "\n";
        }
        else{
            referenceNumber = "[L]Invoice No:[R]"+ transactions.getReceiptNumber() + "\n";
        }
        //Set VatContent
        double vatableSales = transactions.getNetSales() < 0 ? 0 : transactions.getVatableSales();
        double vatAmount = transactions.getNetSales() < 0 ? 0 : transactions.getVatAmount();
        String content = "[C]<b>"+ POSApplication.getInstance().getCompany().getTradeName() +"</b>\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getName() +"\n" +
                "[L]\n" +
                "[C]"+ POSApplication.getInstance().getMachineDetails().getReceiptHeader() +"\n" +
                "[C]"+ unitNumber + POSApplication.getInstance().getBranch().getStreet() + " " + POSApplication.getInstance().getBranch().getBarangayName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getCityName() + " " + POSApplication.getInstance().getBranch().getProvinceName() + "\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getRegionName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getPhoneNumber() +"\n" +
                "[C]VAT REG TIN: "+ POSApplication.getInstance().getMachineDetails().getTinNumber() +"\n" +
                "[C]SERIAL NO: "+ POSApplication.getInstance().getMachineDetails().getSerialNumber() +"\n" +
                "[C]MIN NO: "+ POSApplication.getInstance().getMachineDetails().getMinNumber() +"\n" +
                "[C]MACHINE NO: "+ POSApplication.getInstance().getMachineDetails().getMachineNumber() + "\n" +
                "[L]\n" +
                "[C]<b>"+ label +"</b>\n" +
                "[L]\n" +
                ""+ referenceNumber + "" +
                "[L]DATE:[R]"+ transactions.getCompletedAt() + "\n" +
                "[L]Cashier:[R]"+ transactions.getCashierName() + "\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[L]QTY   DESCRIPTION[R]AMOUNT\n" +
                "[C]------------------------------------------------\n" +
                "" + ordersContent + "" +
                "[L]\n" +
                "[L]Total Qty\n" +
                "[L]"+ generate.toTwoDecimalWithComma(transactions.getTotalQuantity()) +"\n" +
                "[L]\n" +
                "[L]Less VAT 12%[R]"+ generate.toTwoDecimalWithComma(transactions.getVatExpense()) +"\n" +
                "[L]\n" +
                "" + discountContent +
                "[L]VATABLE SALES[R]"+ generate.toTwoDecimalWithComma(vatableSales) +"\n" +
                "[L]VAT AMOUNT[R]"+ generate.toTwoDecimalWithComma(vatAmount) +"\n" +
                "[L]VAT-EXEMPT SALES[R]"+ generate.toTwoDecimalWithComma(transactions.getVatExemptSales()) +"\n" +
                "[L]ZERO-RATED SALES[R]"+ generate.toTwoDecimalWithComma(transactions.getTotalZeroRatedAmount()) +"\n" +
                "[L]\n" +
                "[L]SUB TOTAL[R]"+ generate.toTwoDecimalWithComma(transactions.getGrossSales()) +"\n" +
                "[L]AMOUNT DUE[R]"+ generate.toTwoDecimalWithComma(transactions.getNetSales()) +"\n" +
                "[L]TENDERED[R]"+ generate.toTwoDecimalWithComma(transactions.getTenderAmount()) +"\n" +
                "[L]CHANGE[R]"+ generate.toTwoDecimalWithComma(transactions.getChange()) +"\n" +
                "[L]\n" +
                ""+ paymentContent + "" +
                "[L]\n" +
                "" + discountOtherContent + "" +
                "" + soldToContent + "" +
                "[L]\n" +
                "[L]REMARKS[R]"+ remarks +"\n" +
                "[L]\n" +
                "[C]"+ POSApplication.getInstance().getMachineDetails().getReceiptFooter() +"\n" +
                "[C]THANK YOU COME AGAIN\n" +
                "[L]\n" +
                "[L]POS PROVIDER: "+ BuildConfig.POS_PROVIDER_NAME +"\n" +
                "[L]ADDRESS: "+ BuildConfig.POS_PROVIDER_ADDRESS +"\n" +
                "[L]VAT REG TIN: "+ BuildConfig.POS_PROVIDER_TIN +"\n" +
                "[L]ACCREDITED NO: "+ BuildConfig.POS_PROVIDER_ACCREDITATION +"\n" +
                "[L]PTU NO: "+ BuildConfig.POS_PROVIDER_PTU +"\n" +
                "[L]DATE ISSUED: "+ BuildConfig.POS_PROVIDER_DATE_ISSUED +"\n" +
                "[L]\n" +
                "[L]\n" +
                "" + returnContent + "" +
                //"[C]THIS RECEIPT SHALL BE VALID FOR\n" +
                //"[C]FIVE("+ BuildConfig.POS_OR_VALID_YEAR +") YEARS FROM THE DATE OF\n" +
                //"[C]THE PERMIT TO USE\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        return content;
    }

    public String voidReceipt(Transactions transactions, List<Orders> orders, List<Payments> payments, List<Discounts> discounts){
        Dates date = new Dates();
        Generate generate = new Generate();
        Functions functions = new Functions();
        String ordersContent = "";
        String unitNumber = "";
        String paymentContent = "";
        String discountContent = "";
        String discountOtherContent = "";
        String voidFooterContent = "";
        String soldToContent = "";
        String remarks = "";
        String referenceNumber = "";
        String label = "";
        //Order List
        for(Orders item : orders){
            String vatValue = item.getVatableSales() != 0 ? "V " : item.getVatExemptSales() != 0 ? "E " : "";
            ordersContent += "[L]"+ item.getQty() +"   "+ item.getName() + " @ " + generate.toTwoDecimalWithComma(item.getAmount())  + "[R]"+ generate.toTwoDecimalWithComma(item.getTotal()) + " " + vatValue +"\n";
            ordersContent += "\n";
            if(item.getSerialNumber() != null){
                if(!item.getSerialNumber().equals("")) ordersContent += "[L]S/N: " + item.getSerialNumber() + "\n";
            }
        }
        //Payment List
        paymentContent = "[L]PAYMENT TYPE\n";
        paymentContent += functions.filterByPaymentTypes(payments, paymentOtherInformationsViewModel);
        if(discounts.size() != 0){
            for(Discounts item : discounts){
                if(item.getIsZeroRated() != 1){
                    discountContent += "[L]" + item.getDiscountName() + "[R]" + generate.toTwoDecimalWithComma(item.getDiscountAmount()) + "\n";
                    //This will set the other information of the discount like the name and address.
                    try {
                        List<DiscountOtherInformations> discountOtherInformations = discountOtherInformationsViewModel.fetchByTransactionDiscountId(item.getId());
                        if(discountOtherInformations.size() != 0){
                            discountOtherContent += "[L]" + item.getDiscountName().toUpperCase() + "\n";
                            for(DiscountOtherInformations others : discountOtherInformations){
                                discountOtherContent += "[L]" + others.getName() + " " + others.getValue() + "\n";
                            }
                            discountOtherContent += "\n";
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        //Unit Number
        if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
            unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + "/" + POSApplication.getInstance().getBranch().getFloorNumber() + " ";
        }
        else{
            if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() == null){
                unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + " ";
            }
            else if(POSApplication.getInstance().getBranch().getUnitNumber() == null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
                unitNumber = POSApplication.getInstance().getBranch().getFloorNumber() + " ";
            }
        }
        //Set Void Footer Content
        voidFooterContent += "[C]THIS DOCUMENT IS NOT VALID\n";
        voidFooterContent += "[C]FOR CLAIMING INPUT TAXES\n";
        //Sold To Checking
        try {
            OfficialReceiptInformation officialReceiptInformation = officialReceiptInformationViewModel.fetchByTransactionId(transactions.getId());
            if(officialReceiptInformation != null){
                soldToContent = "[L]SOLD TO\n" +
                        "[L]NAME: [R]"+ officialReceiptInformation.getName() +"\n" +
                        "[L]ADDRESS: [R]"+ officialReceiptInformation.getAddress() +"\n" +
                        "[L]TIN#: [R]"+ officialReceiptInformation.getTin() +"\n" +
                        "[L]BUSINESS STYLE: [R]"+ officialReceiptInformation.getBusinessStyle() +"\n";
            }
            else{
                soldToContent = "[L]SOLD TO\n" +
                        "[L]NAME: ________________________________________\n" +
                        "[L]ADDRESS: _____________________________________\n" +
                        "[L]TIN#: ________________________________________\n" +
                        "[L]BUSINESS STYLE: ______________________________\n";
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Set remarks
        if(transactions.getVoidRemarks() != null){
            remarks = transactions.getVoidRemarks();
        }
        //Set label
        if(transactions.getIsAccountReceivable() == 1){
            label = "[C]<b>*****VOID ACCOUNT RECEIVABLE*****</b>\n";
        }
        else{
            label = "[C]<b>*****VOID*****</b>\n";
        }
        //Set reference number
        if(transactions.getReceiptNumber() == null){
            referenceNumber = "[L]Control No:[R]"+ transactions.getControlNumber() + "\n";
        }
        else {
            if(transactions.getReceiptNumber().isEmpty()){
                referenceNumber = "[L]Control No:[R]"+ transactions.getControlNumber() + "\n";
            }
            else{
                referenceNumber = "[L]Invoice No:[R]"+ transactions.getReceiptNumber() + "\n";
            }
        }
        //Set VatContent
        double vatableSales = transactions.getNetSales() < 0 ? 0 : transactions.getVatableSales();
        double vatAmount = transactions.getNetSales() < 0 ? 0 : transactions.getVatAmount();
        String content = "[C]<b>"+ POSApplication.getInstance().getCompany().getTradeName() +"</b>\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getName() +"\n" +
                "[L]\n" +
                "[C]"+ POSApplication.getInstance().getMachineDetails().getReceiptHeader() +"\n" +
                "[C]"+ unitNumber + POSApplication.getInstance().getBranch().getStreet() + " " + POSApplication.getInstance().getBranch().getBarangayName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getCityName() + " " + POSApplication.getInstance().getBranch().getProvinceName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getRegionName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getPhoneNumber() +"\n" +
                "[C]VAT REG TIN: "+ POSApplication.getInstance().getMachineDetails().getTinNumber() +"\n" +
                "[C]SERIAL NO: "+ POSApplication.getInstance().getMachineDetails().getSerialNumber() +"\n" +
                "[C]MIN NO: "+ POSApplication.getInstance().getMachineDetails().getMinNumber() +"\n" +
                "[C]MACHINE NO: "+ POSApplication.getInstance().getMachineDetails().getMachineNumber() + "\n" +
                "[L]\n" +
                "" + label + "" +
                "[L]\n" +
                "" + referenceNumber + "" +
                "[L]DATE:[R]"+ transactions.getCompletedAt() + "\n" +
                "[L]Cashier:[R]"+ transactions.getCashierName() + "\n" +
                "[L]Void No.:[R]"+ transactions.getVoidCounter() + "\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[L]QTY   DESCRIPTION[R]AMOUNT\n" +
                "[C]------------------------------------------------\n" +
                "" + ordersContent + "" +
                "[L]\n" +
                "[L]Total Qty\n" +
                "[L]"+ generate.toTwoDecimalWithComma(transactions.getTotalQuantity()) +"\n" +
                "[L]\n" +
                "[L]Less VAT 12%[R]"+ generate.toTwoDecimalWithComma(transactions.getVatExpense()) +"\n" +
                "[L]\n" +
                "" + discountContent +
                "[L]VATABLE SALES[R]"+ generate.toTwoDecimalWithComma(vatableSales) +"\n" +
                "[L]VAT AMOUNT[R]"+ generate.toTwoDecimalWithComma(vatAmount) +"\n" +
                "[L]VAT-EXEMPT SALES[R]"+ generate.toTwoDecimalWithComma(transactions.getVatExemptSales()) +"\n" +
                "[L]ZERO-RATED SALES[R]"+ generate.toTwoDecimalWithComma(transactions.getTotalZeroRatedAmount()) +"\n" +
                "[L]\n" +
                "[L]SUB TOTAL[R]"+ generate.toTwoDecimalWithComma(transactions.getGrossSales()) +"\n" +
                "[L]AMOUNT DUE[R]"+ generate.toTwoDecimalWithComma(transactions.getNetSales()) +"\n" +
                "[L]TENDERED[R]"+ generate.toTwoDecimalWithComma(transactions.getTenderAmount()) +"\n" +
                "[L]CHANGE[R]"+ generate.toTwoDecimalWithComma(transactions.getChange()) +"\n" +
                "[L]\n" +
                ""+ paymentContent +"" +
                "[L]\n" +
                "" + discountOtherContent + "" +
                "" + soldToContent + "" +
                "[L]\n" +
                "[L]REMARKS[R]"+ remarks +"\n" +
                "[L]\n" +
                "[C]"+ POSApplication.getInstance().getMachineDetails().getReceiptFooter() +"\n" +
                "[C]THANK YOU COME AGAIN\n" +
                "[L]\n" +
                "[L]POS PROVIDER: "+ BuildConfig.POS_PROVIDER_NAME +"\n" +
                "[L]ADDRESS: "+ BuildConfig.POS_PROVIDER_ADDRESS +"\n" +
                "[L]VAT REG TIN: "+ BuildConfig.POS_PROVIDER_TIN +"\n" +
                "[L]ACCREDITED NO: "+ BuildConfig.POS_PROVIDER_ACCREDITATION +"\n" +
                "[L]PTU NO: "+ BuildConfig.POS_PROVIDER_PTU +"\n" +
                "[L]DATE ISSUED: "+ BuildConfig.POS_PROVIDER_DATE_ISSUED +"\n" +
                "[L]\n" +
                "[L]\n" +
                "" + voidFooterContent + "" +
                //"[C]THIS RECEIPT SHALL BE VALID FOR\n" +
                //"[C]FIVE("+ BuildConfig.POS_OR_VALID_YEAR +") YEARS FROM THE DATE OF\n" +
                //"[C]THE PERMIT TO USE\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        return content;
    }

    public String rePrint(Transactions transactions, List<Orders> orders, List<Payments> payments, List<Discounts> discounts){
        Dates date = new Dates();
        Generate generate = new Generate();
        Functions functions = new Functions();
        String label = "";
        String ordersContent = "";
        String unitNumber = "";
        String paymentContent = "";
        String discountContent = "";
        String discountOtherContent = "";
        String footerContent = "";
        String soldToContent = "";
        String voidCounterContent = "";
        String remarks = "";
        //Order List
        for(Orders item : orders){
            String vatValue = item.getVatableSales() != 0 ? "V " : item.getVatExemptSales() != 0 ? "E " : "";
            ordersContent += "[L]"+ item.getQty() +"   "+ item.getName() + " @ " + generate.toTwoDecimalWithComma(item.getAmount())  + "[R]"+ generate.toTwoDecimalWithComma(item.getTotal()) + " " + vatValue +"\n";
            ordersContent += "\n";
            if(item.getSerialNumber() != null){
                if(!item.getSerialNumber().equals("")) ordersContent += "[L]S/N: " + item.getSerialNumber() + "\n";
            }
        }
        //Payment List
        paymentContent = "[L]PAYMENT TYPE\n";
        paymentContent += functions.filterByPaymentTypes(payments, paymentOtherInformationsViewModel);
        if(discounts.size() != 0){
            for(Discounts item : discounts){
                if(item.getIsZeroRated() != 1){
                    discountContent += "[L]" + item.getDiscountName() + "[R]" + generate.toTwoDecimalWithComma(item.getDiscountAmount()) + "\n";
                    //This will set the other information of the discount like the name and address.
                    try {
                        List<DiscountOtherInformations> discountOtherInformations = discountOtherInformationsViewModel.fetchByTransactionDiscountId(item.getId());
                        if(discountOtherInformations.size() != 0){
                            discountOtherContent += "[L]" + item.getDiscountName().toUpperCase() + "\n";
                            for(DiscountOtherInformations others : discountOtherInformations){
                                discountOtherContent += "[L]" + others.getName() + " " + others.getValue() + "\n";
                            }
                            discountOtherContent += "\n";
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        //Unit Number
        if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
            unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + "/" + POSApplication.getInstance().getBranch().getFloorNumber() + " ";
        }
        else{
            if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() == null){
                unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + " ";
            }
            else if(POSApplication.getInstance().getBranch().getUnitNumber() == null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
                unitNumber = POSApplication.getInstance().getBranch().getFloorNumber() + " ";
            }
        }
        //Label
        if(transactions.getIsVoid() == 0){
            label = "*****REPRINT*****";
        }
        else{
            label = "*****VOID REPRINT*****";
            voidCounterContent = "[L]Void No.:[R]" + transactions.getVoidCounter() + "\n";
        }
        //Set Void Footer Content
        if(transactions.getIsVoid() == 1 || transactions.getIsReturn() == 1){
            footerContent += "[C]THIS DOCUMENT IS NOT VALID\n";
            footerContent += "[C]FOR CLAIMING INPUT TAXES\n";
        }
        //Sold To Checking
        try {
            OfficialReceiptInformation officialReceiptInformation = officialReceiptInformationViewModel.fetchByTransactionId(transactions.getId());
            if(officialReceiptInformation != null){
                soldToContent = "[L]SOLD TO\n" +
                        "[L]NAME: [R]"+ officialReceiptInformation.getName() +"\n" +
                        "[L]ADDRESS: [R]"+ officialReceiptInformation.getAddress() +"\n" +
                        "[L]TIN#: [R]"+ officialReceiptInformation.getTin() +"\n" +
                        "[L]BUSINESS STYLE: [R]"+ officialReceiptInformation.getBusinessStyle() +"\n";
            }
            else{
                soldToContent = "[L]SOLD TO\n" +
                        "[L]NAME: ________________________________________\n" +
                        "[L]ADDRESS: _____________________________________\n" +
                        "[L]TIN#: ________________________________________\n" +
                        "[L]BUSINESS STYLE: ______________________________\n";
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Set Remarks
        if(transactions.getIsVoid() == 0){
            if(transactions.getRemarks() != null){
                remarks = transactions.getRemarks();
            }
        }
        else{
            remarks = transactions.getVoidRemarks();
        }
        //Set VatContent
        double vatableSales = transactions.getNetSales() < 0 ? 0 : transactions.getVatableSales();
        double vatAmount = transactions.getNetSales() < 0 ? 0 : transactions.getVatAmount();
        String content = "[C]<b>"+ POSApplication.getInstance().getCompany().getTradeName() +"</b>\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getName() +"\n" +
                "[L]\n" +
                "[C]"+ POSApplication.getInstance().getMachineDetails().getReceiptHeader() +"\n" +
                "[C]"+ unitNumber + POSApplication.getInstance().getBranch().getStreet() + " " + POSApplication.getInstance().getBranch().getBarangayName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getCityName() + " " + POSApplication.getInstance().getBranch().getProvinceName() + "\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getRegionName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getPhoneNumber() +"\n" +
                "[C]VAT REG TIN: "+ POSApplication.getInstance().getMachineDetails().getTinNumber() +"\n" +
                "[C]SERIAL NO: "+ POSApplication.getInstance().getMachineDetails().getSerialNumber() +"\n" +
                "[C]MIN NO: "+ POSApplication.getInstance().getMachineDetails().getMinNumber() +"\n" +
                "[C]MACHINE NO: "+ POSApplication.getInstance().getMachineDetails().getMachineNumber() + "\n" +
                "[L]\n" +
                "[C]<b>"+ label +"</b>\n" +
                "[L]\n" +
                "[L]Invoice No:[R]"+ transactions.getReceiptNumber() + "\n" +
                "[L]DATE:[R]"+ transactions.getCompletedAt() + "\n" +
                "[L]Cashier:[R]"+ transactions.getCashierName() + "\n" +
                "" + voidCounterContent + "" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[L]QTY   DESCRIPTION[R]AMOUNT\n" +
                "[C]------------------------------------------------\n" +
                "" + ordersContent + "" +
                "[L]\n" +
                "[L]Total Qty\n" +
                "[L]"+ generate.toTwoDecimalWithComma(transactions.getTotalQuantity()) +"\n" +
                "[L]\n" +
                "[L]Less VAT 12%[R]"+ generate.toTwoDecimalWithComma(transactions.getVatExpense()) +"\n" +
                "[L]\n" +
                "" + discountContent +
                "[L]VATABLE SALES[R]"+ generate.toTwoDecimalWithComma(vatableSales) +"\n" +
                "[L]VAT AMOUNT[R]"+ generate.toTwoDecimalWithComma(vatAmount) +"\n" +
                "[L]VAT-EXEMPT SALES[R]"+ generate.toTwoDecimalWithComma(transactions.getVatExemptSales()) +"\n" +
                "[L]ZERO-RATED SALES[R]"+ generate.toTwoDecimalWithComma(transactions.getTotalZeroRatedAmount()) +"\n" +
                "[L]\n" +
                "[L]SUB TOTAL[R]"+ generate.toTwoDecimalWithComma(transactions.getGrossSales()) +"\n" +
                "[L]AMOUNT DUE[R]"+ generate.toTwoDecimalWithComma(transactions.getNetSales()) +"\n" +
                "[L]TENDERED[R]"+ generate.toTwoDecimalWithComma(transactions.getTenderAmount()) +"\n" +
                "[L]CHANGE[R]"+ generate.toTwoDecimalWithComma(transactions.getChange()) +"\n" +
                "[L]\n" +
                ""+ paymentContent +"" +
                "[L]\n" +
                "" + discountOtherContent + "" +
                "" + soldToContent + "" +
                "[L]\n" +
                "[L]REMARKS[R]"+ remarks +"\n" +
                "[L]\n" +
                "[C]"+ POSApplication.getInstance().getMachineDetails().getReceiptFooter() +"\n" +
                "[C]THANK YOU COME AGAIN\n" +
                "[L]\n" +
                "[L]POS PROVIDER: "+ BuildConfig.POS_PROVIDER_NAME +"\n" +
                "[L]ADDRESS: "+ BuildConfig.POS_PROVIDER_ADDRESS +"\n" +
                "[L]VAT REG TIN: "+ BuildConfig.POS_PROVIDER_TIN +"\n" +
                "[L]ACCREDITED NO: "+ BuildConfig.POS_PROVIDER_ACCREDITATION +"\n" +
                "[L]PTU NO: "+ BuildConfig.POS_PROVIDER_PTU +"\n" +
                "[L]DATE ISSUED: "+ BuildConfig.POS_PROVIDER_DATE_ISSUED +"\n" +
                "[L]\n" +
                "[L]\n" +
                "" + footerContent + "" +
                //"[C]THIS RECEIPT SHALL BE VALID FOR\n" +
                //"[C]FIVE("+ BuildConfig.POS_OR_VALID_YEAR +") YEARS FROM THE DATE OF\n" +
                //"[C]THE PERMIT TO USE\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        return content;
    }

    public String statementOfAccount(Transactions transactions, List<Orders> orders, List<Discounts> discounts){
        Dates date = new Dates();
        Generate generate = new Generate();
        String ordersContent = "";
        String unitNumber = "";
        String discountContent = "";
        String discountOtherContent = "";
        String soldToContent = "";
        String remarks = "";
        for(Orders item : orders){
            String vatValue = item.getVatableSales() != 0 ? "V " : item.getVatExemptSales() != 0 ? "E " : "";
            ordersContent += "[L]"+ item.getQty() +"   "+ item.getName() + " @ " + generate.toTwoDecimalWithComma(item.getAmount())  + "[R]"+ generate.toTwoDecimalWithComma(item.getTotal()) + " " + vatValue +"\n";
            ordersContent += "\n";
        }
        if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
            unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + "/" + POSApplication.getInstance().getBranch().getFloorNumber();
        }
        else{
            if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() == null){
                unitNumber = POSApplication.getInstance().getBranch().getUnitNumber();
            }
            else if(POSApplication.getInstance().getBranch().getUnitNumber() == null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
                unitNumber = POSApplication.getInstance().getBranch().getFloorNumber();
            }
        }
        if(discounts.size() != 0){
            for(Discounts item : discounts){
                if(item.getIsZeroRated() != 1){
                    discountContent += "[L]" + item.getDiscountName() + "[R]" + generate.toTwoDecimalWithComma(item.getDiscountAmount()) + "\n";
                    //This will set the other information of the discount like the name and address.
                    try {
                        List<DiscountOtherInformations> discountOtherInformations = discountOtherInformationsViewModel.fetchByTransactionDiscountId(item.getId());
                        if(discountOtherInformations.size() != 0){
                            discountOtherContent += "[L]" + item.getDiscountName().toUpperCase() + "\n";
                            for(DiscountOtherInformations others : discountOtherInformations){
                                discountOtherContent += "[L]" + others.getName() + " " + others.getValue() + "\n";
                            }
                            discountOtherContent += "\n";
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        //Sold To Checking
        try {
            OfficialReceiptInformation officialReceiptInformation = officialReceiptInformationViewModel.fetchByTransactionId(transactions.getId());
            if(officialReceiptInformation != null){
                soldToContent = "[L]SOLD TO\n" +
                        "[L]NAME: [R]"+ officialReceiptInformation.getName() +"\n" +
                        "[L]ADDRESS: [R]"+ officialReceiptInformation.getAddress() +"\n" +
                        "[L]TIN#: [R]"+ officialReceiptInformation.getTin() +"\n" +
                        "[L]BUSINESS STYLE: [R]"+ officialReceiptInformation.getBusinessStyle() +"\n";
            }
            else{
                soldToContent = "[L]SOLD TO\n" +
                        "[L]NAME: ________________________________________\n" +
                        "[L]ADDRESS: _____________________________________\n" +
                        "[L]TIN#: ________________________________________\n" +
                        "[L]BUSINESS STYLE: ______________________________\n";
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Set Remarks
        if(transactions.getRemarks() != null){
            remarks = transactions.getRemarks();
        }
        //Set VatContent
        double vatableSales = transactions.getNetSales() < 0 ? 0 : transactions.getVatableSales();
        double vatAmount = transactions.getNetSales() < 0 ? 0 : transactions.getVatAmount();
        String content = "[C]<b>"+ POSApplication.getInstance().getCompany().getTradeName() +"</b>\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getName() +"\n" +
                "[L]\n" +
                "[C]"+ POSApplication.getInstance().getMachineDetails().getReceiptHeader() +"\n" +
                "[C]"+ unitNumber + " " + POSApplication.getInstance().getBranch().getStreet() + " " + POSApplication.getInstance().getBranch().getBarangayName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getCityName() + " " + POSApplication.getInstance().getBranch().getProvinceName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getRegionName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getPhoneNumber() +"\n" +
                "[C]VAT REG TIN: "+ POSApplication.getInstance().getMachineDetails().getTinNumber() +"\n" +
                "[C]SERIAL NO: "+ POSApplication.getInstance().getMachineDetails().getSerialNumber() +"\n" +
                "[C]MIN NO: "+ POSApplication.getInstance().getMachineDetails().getMinNumber() +"\n" +
                "[C]MACHINE NO: "+ POSApplication.getInstance().getMachineDetails().getMachineNumber() + "\n" +
                "[L]\n" +
                "[C]<b>STATEMENT OF ACCOUNT</b>\n" +
                "[L]\n" +
                "[L]Control No:[R]"+ transactions.getControlNumber() + "\n" +
                "[L]DATE:[R]"+ transactions.getTreg() + "\n" +
                "[L]Cashier:[R]"+ transactions.getCashierName() + "\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[L]QTY   DESCRIPTION[R]AMOUNT\n" +
                "[C]------------------------------------------------\n" +
                "" + ordersContent + "" +
                "[L]\n" +
                "[L]Total Qty\n" +
                "[L]"+ generate.toTwoDecimalWithComma(transactions.getTotalQuantity()) +"\n" +
                "[L]\n" +
                "[L]Less VAT 12%[R]"+ generate.toTwoDecimalWithComma(transactions.getVatExpense()) +"\n" +
                "[L]\n" +
                "" + discountContent +
                "[L]VATABLE SALES[R]"+ generate.toTwoDecimalWithComma(vatableSales) +"\n" +
                "[L]VAT AMOUNT[R]"+ generate.toTwoDecimalWithComma(vatAmount) +"\n" +
                "[L]VAT-EXEMPT SALES[R]"+ generate.toTwoDecimalWithComma(transactions.getVatExemptSales()) +"\n" +
                "[L]ZERO-RATED SALES[R]"+ generate.toTwoDecimalWithComma(transactions.getTotalZeroRatedAmount()) +"\n" +
                "[L]\n" +
                "[L]SUB TOTAL[R]"+ generate.toTwoDecimalWithComma(transactions.getGrossSales()) +"\n" +
                "[L]AMOUNT DUE[R]"+ generate.toTwoDecimalWithComma(transactions.getNetSales()) +"\n" +
                "[L]TENDERED[R]"+ generate.toTwoDecimalWithComma(transactions.getTenderAmount()) +"\n" +
                "[L]CHANGE[R]"+ generate.toTwoDecimalWithComma(transactions.getChange()) +"\n" +
                "[L]\n" +
                "[L]PAYMENT TYPE[R]\n" +
                "[L]\n" +
                "" + discountOtherContent + "" +
                "" + soldToContent + "" +
                "[L]\n" +
                "[L]REMARKS[R]"+ remarks +"\n" +
                "[L]\n" +
                "[C]"+ POSApplication.getInstance().getMachineDetails().getReceiptFooter() +"\n" +
                "[C]THANK YOU COME AGAIN\n" +
                "[L]\n" +
                "[L]POS PROVIDER: "+ BuildConfig.POS_PROVIDER_NAME +"\n" +
                "[L]ADDRESS: "+ BuildConfig.POS_PROVIDER_ADDRESS +"\n" +
                "[L]VAT REG TIN: "+ BuildConfig.POS_PROVIDER_TIN +"\n" +
                "[L]ACCREDITED NO: "+ BuildConfig.POS_PROVIDER_ACCREDITATION +"\n" +
                "[L]PTU NO: "+ BuildConfig.POS_PROVIDER_PTU +"\n" +
                "[L]DATE ISSUED: "+ BuildConfig.POS_PROVIDER_DATE_ISSUED +"\n" +
                "[L]\n" +
                "[L]\n" +
                //"[C]THIS RECEIPT SHALL BE VALID FOR\n" +
                //"[C]FIVE("+ BuildConfig.POS_OR_VALID_YEAR +") YEARS FROM THE DATE OF\n" +
                //"[C]THE PERMIT TO USE\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        return content;
    }

    public String cashFund(CashFund cashFund, List<CashFundDenomination> cashFundDenominations){
        Generate generate = new Generate();
        String denominationDetails = "";
        String unitNumber = "";
        for(CashFundDenomination item : cashFundDenominations){
            if(item.getQty() != 0){
                denominationDetails += "[L]" + item.getName() + "[C]" + item.getQty() + "[R]" + generate.toTwoDecimalWithComma(item.getAmount()) + "\n";
            }
        }
        if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
            unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + "/" + POSApplication.getInstance().getBranch().getFloorNumber();
        }
        else{
            if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() == null){
                unitNumber = POSApplication.getInstance().getBranch().getUnitNumber();
            }
            else if(POSApplication.getInstance().getBranch().getUnitNumber() == null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
                unitNumber = POSApplication.getInstance().getBranch().getFloorNumber();
            }
        }
        String content = "[C]<b>"+ POSApplication.getInstance().getCompany().getTradeName() +"</b>\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getName() +"\n" +
                "[C]"+ unitNumber + " " + POSApplication.getInstance().getBranch().getStreet() + " " + POSApplication.getInstance().getBranch().getBarangayName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getCityName() + " " + POSApplication.getInstance().getBranch().getProvinceName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getRegionName() +"\n" +
                "[C]VAT REG TIN: "+ POSApplication.getInstance().getMachineDetails().getTinNumber() +"\n" +
                "[C]PERMIT TO USE NO: "+ POSApplication.getInstance().getMachineDetails().getPermitNumber() +"\n" +
                "[C]SERIAL NO: "+ POSApplication.getInstance().getMachineDetails().getSerialNumber() +"\n" +
                "[C]MACHINE NO: "+ POSApplication.getInstance().getMachineDetails().getMachineNumber() +"\n" +
                "[C]SHIFT NO: "+ cashFund.getShiftNumber() +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]CASH FUND\n" +
                "[L]Denominations[C]QTY[R]Amount\n" +
                "[C]------------------------------------------------\n" +
                "" + denominationDetails + "" +
                "[L]\n" +
                "[L]Sub Total[R]"+ generate.toTwoDecimalWithComma(cashFund.getAmount()) +"\n" +
                "[C]------------------------------------------------\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n" +
                "[C]"+ cashFund.getCashierName() +"\n" +
                "[C]------------------------------------------------\n" +
                "[C]Cashier Name / Signature\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        return content;
    }

    public String safeKeeping(Safekeeping safekeeping, List<SafekeepingDenomination> safekeepingDenomination){
        Dates date = new Dates();
        Generate generate = new Generate();
        String denominationDetails = "";
        String unitNumber = "";
        for(SafekeepingDenomination item : safekeepingDenomination){
            if(item.getQty() != 0){
                denominationDetails += "[L]" + item.getName() + "[C]" + item.getQty() + "[R]" + generate.toTwoDecimalWithComma(item.getTotal()) + "\n";
            }
        }
        if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
            unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + "/" + POSApplication.getInstance().getBranch().getFloorNumber();
        }
        else{
            if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() == null){
                unitNumber = POSApplication.getInstance().getBranch().getUnitNumber();
            }
            else if(POSApplication.getInstance().getBranch().getUnitNumber() == null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
                unitNumber = POSApplication.getInstance().getBranch().getFloorNumber();
            }
        }
        String content = "[C]<b>"+ POSApplication.getInstance().getCompany().getTradeName() +"</b>\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getName() +"\n" +
                "[C]"+ unitNumber + " " + POSApplication.getInstance().getBranch().getStreet() + " " + POSApplication.getInstance().getBranch().getBarangayName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getCityName() + " " + POSApplication.getInstance().getBranch().getProvinceName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getRegionName() +"\n" +
                "[C]VAT REG TIN: "+ POSApplication.getInstance().getMachineDetails().getTinNumber() +"\n" +
                "[C]PERMIT TO USE NO: "+ POSApplication.getInstance().getMachineDetails().getPermitNumber() +"\n" +
                "[C]SERIAL NO: "+ POSApplication.getInstance().getMachineDetails().getSerialNumber() +"\n" +
                "[C]MACHINE NO: "+ POSApplication.getInstance().getMachineDetails().getMachineNumber() +"\n" +
                "[C]SHIFT NO: "+ safekeeping.getShiftNumber() +"\n" +
                "[L]DATE: "+ date.nowDateOnly(safekeeping.getTreg()) +"\n" +
                "[L]TIME: "+ date.timeNow(safekeeping.getTreg()) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Safekeeping\n" +
                "[L]Denominations[C]QTY[R]Amount\n" +
                "[C]------------------------------------------------\n" +
                "" + denominationDetails + "" +
                "[L]\n" +
                "[L]Sub Total[R]"+ generate.toTwoDecimalWithComma(safekeeping.getAmount()) +"\n" +
//                "[L]Cashier's Over / Shortage[R]"+ generate.toTwoDecimalWithComma(safekeeping.getShortOver()) +"\n" +
                "[C]------------------------------------------------\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n" +
                "[C]"+ safekeeping.getCashierName() +"\n" +
                "[C]------------------------------------------------\n" +
                "[C]Cashier Name / Signature\n" +
                "[L]\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Manager / Supervisor\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        return content;
    }

    public String cutOff(CutOff cutOff, List<CutOffDiscounts> cutOffDiscounts, List<CutOffPayments> cutOffPayments, List<SafekeepingDenomination> lastSKDenominations, List<CashFund> cashFunds, List<CutOffDepartments> cutOffDepartments){
        Dates date = new Dates();
        Generate generate = new Generate();
        double totalDiscountAmount = 0.00, totalPaymentAmount = 0.00, totalChangeFund = 0.00, totalCashExcludingChangeFund = 0.00, totalLastSKAmount = 0.00, totalDepartmentAmount = 0.00, countDepartment = 0.00;
        int countDiscount = 0, countPayment = 0;
        String safekeepingDetails = "";
        String discountDetails = "";
        String paymentDetails = "";
        String unitNumber = "";
        String departmentDetails = "";
        if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
            unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + "/" + POSApplication.getInstance().getBranch().getFloorNumber();
        }
        else{
            if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() == null){
                unitNumber = POSApplication.getInstance().getBranch().getUnitNumber();
            }
            else if(POSApplication.getInstance().getBranch().getUnitNumber() == null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
                unitNumber = POSApplication.getInstance().getBranch().getFloorNumber();
            }
        }
        if(cutOffPayments.size() != 0){
            for(CutOffPayments item : cutOffPayments){
                paymentDetails += "[L]"+ item.getName() +"[C]"+ item.getTransactionCount() +"[R]"+ generate.toTwoDecimalWithComma(item.getAmount()) +"\n";
                totalPaymentAmount += item.getAmount();
                countPayment += item.getTransactionCount();
            }
        }
        else{
            paymentDetails = "\n";
        }
        if(cutOffDiscounts.size() != 0){
            for(CutOffDiscounts item : cutOffDiscounts){
                if(item.getIsZeroRated() != 1){
                    discountDetails += "[L]"+ item.getName() +"[C]"+ item.getTransactionCount() +"[R]"+ generate.toTwoDecimalWithComma(item.getAmount()) +"\n";
                    totalDiscountAmount += item.getAmount();
                    countDiscount += item.getTransactionCount();
                }
            }
        }
        else{
            discountDetails = "\n";
        }
        if(lastSKDenominations.size() != 0){
            for(SafekeepingDenomination item : lastSKDenominations){
                if(item.getQty() != 0){
                    safekeepingDetails += "[L]" + item.getName() + "[C]" + item.getQty() + "[R]" + generate.toTwoDecimalWithComma(item.getTotal()) + "\n";
                    totalLastSKAmount += item.getTotal();
                }
            }
        }
        else{
            safekeepingDetails = "\n";
        }
        if(cashFunds.size() != 0){
            for(CashFund item : cashFunds){
                totalChangeFund += item.getAmount();
            }
        }
        if(cutOffDepartments.size() != 0){
            for(CutOffDepartments item : cutOffDepartments){
                departmentDetails += "[L]"+ item.getName() +"[C]"+ item.getTransactionCount() +"[R]"+ generate.toTwoDecimalWithComma(item.getAmount()) +"\n";
                totalDepartmentAmount += item.getAmount();
                countDepartment += item.getTransactionCount();
            }
        }
        else{
            departmentDetails = "\n";
        }
        totalCashExcludingChangeFund = cutOff.getTotalSK() - totalChangeFund;
        String content = "[C]<b>"+ POSApplication.getInstance().getCompany().getTradeName() +"</b>\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getName() +"\n" +
                "[C]"+ unitNumber + " " + POSApplication.getInstance().getBranch().getStreet() + " " + POSApplication.getInstance().getBranch().getBarangayName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getCityName() + " " + POSApplication.getInstance().getBranch().getProvinceName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getRegionName() +"\n" +
                "[C]VAT REG TIN: "+ POSApplication.getInstance().getMachineDetails().getTinNumber() +"\n" +
                "[C]PERMIT TO USE NO: "+ POSApplication.getInstance().getMachineDetails().getPermitNumber() +"\n" +
                "[C]SERIAL NO: "+ POSApplication.getInstance().getMachineDetails().getSerialNumber() +"\n" +
                "[C]MACHINE NO: "+ POSApplication.getInstance().getMachineDetails().getMachineNumber() +"\n" +
                "[L]\n" +
                "{{HEADER}}" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]X Reading Report\n" +
                "[C]------------------------------------------------\n" +
                "[L]Date [R]"+ date.nowDateOnly(cutOff.getTreg()) +"\n" +
                "[L]Time [R]"+ date.timeNow(cutOff.getTreg()) +"\n" +
                "[L]Cashier Name [R]"+ cutOff.getCashierName() +"\n" +
                "[L]X Count [R]"+ cutOff.getReadingNumber() +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Sales Details\n" +
                "[C]------------------------------------------------\n" +
                "[L]Vatable Sales [R]"+ generate.toTwoDecimalWithComma(cutOff.getVatableSales()) +"\n" +
                "[L]Vat Amount [R]"+ generate.toTwoDecimalWithComma(cutOff.getVatAmount()) +"\n" +
                "[L]Vat Exempt Sales [R]"+ generate.toTwoDecimalWithComma(cutOff.getVatExemptSales()) +"\n" +
                "[L]Zero Rated Sales [R]"+ generate.toTwoDecimalWithComma(cutOff.getTotalZeroRatedAmount()) +"\n" +
                "[L]<b>Gross Sales</b> [R]"+ generate.toTwoDecimalWithComma(cutOff.getGrossSales()) +"\n" +
                "[L]<b>Net Sales</b> [R]"+ generate.toTwoDecimalWithComma(cutOff.getGrossSales() - cutOff.getVatAmount() - cutOff.getTotalDiscountAmount()) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Discount Details\n" +
                "[L]Disc. Type[C]Trans. Count[R]Amount\n" +
                "[C]------------------------------------------------\n" +
                "" + discountDetails + "" +
                "[L]<b>Total Discount</b>[C]"+ countDiscount +"[R]"+ generate.toTwoDecimalWithComma(totalDiscountAmount) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Payment Type\n" +
                "[L]Particulars[C]Trans. Count[R]Amount\n" +
                "[C]------------------------------------------------\n" +
                "" + paymentDetails + "" +
                "[L]<b>Total Receipts</b>[C]" + countPayment + "[R]"+ generate.toTwoDecimalWithComma(totalPaymentAmount) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Cash Drawer Breakdown\n" +
                "[L]Denominations[C]QTY[R]Amount\n" +
                "[C]------------------------------------------------\n" +
                "" + safekeepingDetails + "" +
                "[L]Safekeeping[R]"+ generate.toTwoDecimalWithComma(cutOff.getTotalSK() - totalLastSKAmount) +"\n" +
                "[L]Sub Total[R]"+ generate.toTwoDecimalWithComma(cutOff.getTotalSK()) +"\n" +
                "[L]Less: Change Fund[R]"+ generate.toTwoDecimalWithComma(totalChangeFund) +"\n" +
                "[L]Total Cash Excluding Change Fund[R]"+ generate.toTwoDecimalWithComma(totalCashExcludingChangeFund) +"\n" +
                "[L]Cashier's Over / Shortage[R]"+ generate.toTwoDecimalWithComma(cutOff.getTotalShortOver() - totalChangeFund) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Void Details\n" +
                "[C]------------------------------------------------\n" +
                "[L]Void Quantity[R]"+ cutOff.getVoidQty() +"\n" +
                "[L]Void Amount[R]"+ generate.toTwoDecimalWithComma(cutOff.getVoidAmount()) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Return Details\n" +
                "[C]------------------------------------------------\n" +
                "[L]Return Amount[R]"+ generate.toTwoDecimalWithComma(cutOff.getTotalReturn()) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Accumulated Sales\n" +
                "[C]------------------------------------------------\n" +
                "[L]Beg. SI Number[R]"+ cutOff.getBeginningOR() +"\n" +
                "[L]End. SI Number[R]"+ cutOff.getEndingOR() +"\n" +
                "[L]Item/Cust. Count[R]0\n" +
                "[L]End of Shift[R]"+ cutOff.getShiftNumber() +"\n" +
                "[L]Old Grand Total[R]"+ generate.toTwoDecimalWithComma(cutOff.getBeginningAmount()) +"\n" +
                "[L]New Grand Total[R]"+ generate.toTwoDecimalWithComma(cutOff.getEndingAmount()) +"\n" +
                "[C]------------------------------------------------\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Sales By Department\n" +
                "[L]Department[C]Trans. Count[R]Amount\n" +
                "[C]------------------------------------------------\n" +
                "" + departmentDetails + "" +
                "[L]<b>Total</b>[C]"+ generate.toTwoDecimalWithComma(countDepartment) +"[R]"+ generate.toTwoDecimalWithComma(totalDepartmentAmount) +"\n" +
                "[C]------------------------------------------------\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n" +
                "[C]" + cutOff.getCashierName() + "\n" +
                "[C]------------------------------------------------\n" +
                "[C]Cashier Name / Signature\n" +
                "[L]\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Manager / Supervisor\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        return content;
    }

    public String endOfDay(EndOfDay endOfDay, List<EndOfDayDepartments> endOfDayDepartments, List<EndOfDayDiscounts> endOfDayDiscounts, List<EndOfDayPayments> endOfDayPayments){
        Dates date = new Dates();
        Generate generate = new Generate();
        double totalDiscountAmount = 0.00, totalPaymentAmount = 0.00, totalDepartmentAmount = 0.00, countDepartment = 0.00;
        int countDiscount = 0, countPayment = 0;
        String discountDetails = "";
        String paymentDetails = "";
        String departmentDetails = "";
        String unitNumber = "";
        if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
            unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + "/" + POSApplication.getInstance().getBranch().getFloorNumber();
        }
        else{
            if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() == null){
                unitNumber = POSApplication.getInstance().getBranch().getUnitNumber();
            }
            else if(POSApplication.getInstance().getBranch().getUnitNumber() == null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
                unitNumber = POSApplication.getInstance().getBranch().getFloorNumber();
            }
        }
        if(endOfDayPayments.size() != 0){
            for(EndOfDayPayments item : endOfDayPayments){
                paymentDetails += "[L]"+ item.getName() +"[C]"+ item.getTransactionCount() +"[R]"+ generate.toTwoDecimalWithComma(item.getAmount()) +"\n";
                totalPaymentAmount += item.getAmount();
                countPayment += item.getTransactionCount();
            }
        }
        else{
            paymentDetails = "\n";
        }
        if(endOfDayDiscounts.size() != 0){
            for(EndOfDayDiscounts item : endOfDayDiscounts){
                if(item.getIsZeroRated() != 1){
                    discountDetails += "[L]"+ item.getName() +"[C]"+ item.getTransactionCount() +"[R]"+ generate.toTwoDecimalWithComma(item.getAmount()) +"\n";
                    totalDiscountAmount += item.getAmount();
                    countDiscount += item.getTransactionCount();
                }
            }
        }
        else{
            discountDetails = "\n";
        }
        if(endOfDayDepartments.size() != 0){
            for(EndOfDayDepartments item : endOfDayDepartments){
                departmentDetails += "[L]"+ item.getName() +"[C]"+ item.getTransactionCount() +"[R]"+ generate.toTwoDecimalWithComma(item.getAmount()) +"\n";
                totalDepartmentAmount += item.getAmount();
                countDepartment += item.getTransactionCount();
            }
        }
        else{
            departmentDetails = "\n";
        }
        String content = "[C]<b>"+ POSApplication.getInstance().getCompany().getTradeName() +"</b>\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getName() +"\n" +
                "[C]"+ unitNumber + " " + POSApplication.getInstance().getBranch().getStreet() + " " + POSApplication.getInstance().getBranch().getBarangayName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getCityName() + " " + POSApplication.getInstance().getBranch().getProvinceName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getRegionName() +"\n" +
                "[C]VAT REG TIN: "+ POSApplication.getInstance().getMachineDetails().getTinNumber() +"\n" +
                "[C]PERMIT TO USE NO: "+ POSApplication.getInstance().getMachineDetails().getPermitNumber() +"\n" +
                "[C]SERIAL NO: "+ POSApplication.getInstance().getMachineDetails().getSerialNumber() +"\n" +
                "[C]MACHINE NO: "+ POSApplication.getInstance().getMachineDetails().getMachineNumber() +"\n" +
                "[L]\n" +
                "{{HEADER}}" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Z Reading Report\n" +
                "[C]------------------------------------------------\n" +
                "[L]Date [R]"+ date.nowDateOnly(endOfDay.getGeneratedDate()) +"\n" +
                "[L]Time [R]"+ date.timeNow(endOfDay.getGeneratedDate()) +"\n" +
                "[L]Date Coverage [R]"+ date.nowDateOnly(endOfDay.getTreg()) +" " + date.timeNow(endOfDay.getTreg()) + "\n" +
                "[L]Z Count [R]"+ endOfDay.getReadingNumber() +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Sales Details\n" +
                "[C]------------------------------------------------\n" +
                "[L]Vatable Sales [R]"+ generate.toTwoDecimalWithComma(endOfDay.getVatableSales()) +"\n" +
                "[L]Vat Amount [R]"+ generate.toTwoDecimalWithComma(endOfDay.getVatAmount()) +"\n" +
                "[L]Vat Exempt Sales [R]"+ generate.toTwoDecimalWithComma(endOfDay.getVatExemptSales()) +"\n" +
                "[L]Zero Rated Sales [R]"+ generate.toTwoDecimalWithComma(endOfDay.getTotalZeroRatedAmount()) +"\n" +
                "[L]<b>Gross Sales</b> [R]"+ generate.toTwoDecimalWithComma(endOfDay.getGrossSales()) +"\n" +
                "[L]<b>Net Sales</b> [R]"+ generate.toTwoDecimalWithComma(endOfDay.getGrossSales() - endOfDay.getVatAmount() - endOfDay.getTotalDiscountAmount()) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Payment Type\n" +
                "[L]Particulars[C]Trans. Count[R]Amount\n" +
                "[C]------------------------------------------------\n" +
                "" + paymentDetails + "" +
                "[L]<b>Total Receipts</b>[C]"+ countPayment +"[R]"+ generate.toTwoDecimalWithComma(totalPaymentAmount) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Discount Details\n" +
                "[L]Disc. Type[C]Trans. Count[R]Amount\n" +
                "[C]------------------------------------------------\n" +
                "" + discountDetails + "" +
                "[L]<b>Total Discount</b>[C]"+ countDiscount +"[R]"+ generate.toTwoDecimalWithComma(totalDiscountAmount) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Void Details\n" +
                "[C]------------------------------------------------\n" +
                "[L]Void Quantity[R]"+ endOfDay.getVoidQty() +"\n" +
                "[L]Void Amount[R]"+ generate.toTwoDecimalWithComma(endOfDay.getVoidAmount()) +"\n" +
                "[L]\n" +
                "[C]Return Details\n" +
                "[C]------------------------------------------------\n" +
                "[L]Return Amount[R]"+ generate.toTwoDecimalWithComma(endOfDay.getTotalReturn()) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Accumulated Sales\n" +
                "[C]------------------------------------------------\n" +
                "[L]Beg. SI Number[R]"+ endOfDay.getBeginningOR() +"\n" +
                "[L]End. SI Number[R]"+ endOfDay.getEndingOR() +"\n" +
                "[L]Item/Cust. Count[R]0\n" +
                "[L]Old Grand Total[R]"+ generate.toTwoDecimalWithComma(endOfDay.getBeginningAmount()) +"\n" +
                "[L]New Grand Total[R]"+ generate.toTwoDecimalWithComma(endOfDay.getEndingAmount()) +"\n" +
                "[C]------------------------------------------------\n" +
                //"[L]\n" +
                //"[C]------------------------------------------------\n" +
                //"[C]Sales By Department\n" +
                //"[L]Department[C]Trans. Count[R]Amount\n" +
                //"[C]------------------------------------------------\n" +
                //"" + departmentDetails + "" +
                //"[L]<b>Total</b>[C]"+ generate.toTwoDecimalWithComma(countDepartment) +"[R]"+ generate.toTwoDecimalWithComma(totalDepartmentAmount) +"\n" +
                //"[C]------------------------------------------------\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        return content;
    }

    public String spotAudit(SpotAudit spotAudit, List<SpotAuditDenomination> spotAuditDenominations){
        Dates date = new Dates();
        Generate generate = new Generate();
        double totalDiscountAmount = 0.00, totalPaymentAmount = 0.00;
        String safekeepingDetails = "";
        String discountDetails = "";
        String paymentDetails = "";
        String unitNumber = "";
        if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
            unitNumber = POSApplication.getInstance().getBranch().getUnitNumber() + "/" + POSApplication.getInstance().getBranch().getFloorNumber();
        }
        else{
            if(POSApplication.getInstance().getBranch().getUnitNumber() != null && POSApplication.getInstance().getBranch().getFloorNumber() == null){
                unitNumber = POSApplication.getInstance().getBranch().getUnitNumber();
            }
            else if(POSApplication.getInstance().getBranch().getUnitNumber() == null && POSApplication.getInstance().getBranch().getFloorNumber() != null){
                unitNumber = POSApplication.getInstance().getBranch().getFloorNumber();
            }
        }
        String content = "[C]<b>"+ POSApplication.getInstance().getCompany().getTradeName() +"</b>\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getName() +"\n" +
                "[C]"+ unitNumber + " " + POSApplication.getInstance().getBranch().getStreet() + " " + POSApplication.getInstance().getBranch().getBarangayName() +"\n" +
                "[C]"+ POSApplication.getInstance().getBranch().getCityName() + " " + POSApplication.getInstance().getBranch().getProvinceName() +"\n" +
                "[C]" + POSApplication.getInstance().getBranch().getRegionName() +"\n" +
                "[C]VAT REG TIN: "+ POSApplication.getInstance().getMachineDetails().getTinNumber() +"\n" +
                "[C]PERMIT TO USE NO: "+ POSApplication.getInstance().getMachineDetails().getPermitNumber() +"\n" +
                "[C]SERIAL NO: "+ POSApplication.getInstance().getMachineDetails().getSerialNumber() +"\n" +
                "[C]MACHINE NO: "+ POSApplication.getInstance().getMachineDetails().getMachineNumber() +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]X Reading Report\n" +
                "[C]------------------------------------------------\n" +
                "[L]Date [R]"+ date.nowDateOnly(spotAudit.getTreg()) +"\n" +
                "[L]Time [R]"+ date.timeNow(spotAudit.getTreg()) +"\n" +
                "[L]Cashier Name [R]"+ spotAudit.getCashierName() +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Sales Details\n" +
                "[C]------------------------------------------------\n" +
                "[L]Vatable Sales [R]"+ generate.toTwoDecimalWithComma(spotAudit.getVatableSales()) +"\n" +
                "[L]Vat Amount [R]"+ generate.toTwoDecimalWithComma(spotAudit.getVatAmount()) +"\n" +
                "[L]Vat Exempt Sales [R]"+ generate.toTwoDecimalWithComma(spotAudit.getVatExemptSales()) +"\n" +
                "[L]Zero Rated Sales [R]0.00\n" +
                "[L]<b>Gross Sales</b> [R]"+ generate.toTwoDecimalWithComma(spotAudit.getGrossSales()) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Cash Drawer Breakdown\n" +
                "[L]Denominations[C]QTY[R]Amount\n" +
                "[C]------------------------------------------------\n" +
                "" + safekeepingDetails + "" +
                "[L]Safekeeping\n" +
                "[L]Sub Total[R]"+ generate.toTwoDecimalWithComma(spotAudit.getSafekeepingAmount()) +"\n" +
                "[L]Less: Change Fund\n" +
                "[L]Total Cash Excluding Change Fund\n" +
                "[L]Cashier's Over / Shortage[R]"+ generate.toTwoDecimalWithComma(spotAudit.getTotalShortOver()) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Void Details\n" +
                "[C]------------------------------------------------\n" +
                "[L]Void Quantity[R]"+ spotAudit.getVoidQty() +"\n" +
                "[L]Void Amount[R]"+ generate.toTwoDecimalWithComma(spotAudit.getVoidAmount()) +"\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Accumulated Sales\n" +
                "[C]------------------------------------------------\n" +
                "[L]Beg. SI Number[R]"+ spotAudit.getBeginningOR() +"\n" +
                "[L]End. SI Number[R]"+ spotAudit.getEndingOR() +"\n" +
                "[L]Item/Cust. Count[R]0\n" +
                "[L]End of Shift[R]"+ spotAudit.getShiftNumber() +"\n" +
                "[L]Old Grand Total[R]"+ generate.toTwoDecimalWithComma(spotAudit.getBeginningAmount()) +"\n" +
                "[L]New Grand Total[R]"+ generate.toTwoDecimalWithComma(spotAudit.getEndingAmount()) +"\n" +
                "[C]------------------------------------------------\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n" +
                "[C]" + spotAudit.getCashierName() + "\n" +
                "[C]------------------------------------------------\n" +
                "[C]Cashier Name / Signature\n" +
                "[L]\n" +
                "[L]\n" +
                "[C]------------------------------------------------\n" +
                "[C]Manager / Supervisor\n" +
                "[L]\n" +
                "[L]\n" +
                "[L]\n";
        return content;
    }

}
