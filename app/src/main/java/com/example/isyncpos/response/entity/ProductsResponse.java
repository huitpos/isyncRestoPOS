package com.example.isyncpos.response.entity;

import java.util.List;

public class ProductsResponse {

    private boolean success;
    private List<DataClass> data;
    private String message;
    private String code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataClass> getData() {
        return data;
    }

    public void setData(List<DataClass> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public class DataClass {

        private int id;

        private int company_id;

        private int department_id;

        private int category_id;

        private int subcategory_id;

        private int uom_id;

        private int item_type_id;

        private String image;

        private String code;

        private String barcode;

        private String name;

        private String description;

        private String abbreviation;

        private double srp;

        private double cost;

        private double markup;

        private int with_serial;

        private int discount_exempt;

        private int open_price;

        private String status;

        private int minimum_stock_level;

        private int maximum_stock_level;

        private double stock_on_hand;

        private int vat_exempt;

        private List<BundleClass> bundled_items;

        private ItemTypeClass item_type;

        private UOMClass uom;

        private List<ItemLocationClass> item_locations;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public int getDepartment_id() {
            return department_id;
        }

        public void setDepartment_id(int department_id) {
            this.department_id = department_id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getSubcategory_id() {
            return subcategory_id;
        }

        public void setSubcategory_id(int subcategory_id) {
            this.subcategory_id = subcategory_id;
        }

        public int getUom_id() {
            return uom_id;
        }

        public void setUom_id(int uom_id) {
            this.uom_id = uom_id;
        }

        public int getItem_type_id() {
            return item_type_id;
        }

        public void setItem_type_id(int item_type_id) {
            this.item_type_id = item_type_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public double getSrp() {
            return srp;
        }

        public void setSrp(double srp) {
            this.srp = srp;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public double getMarkup() {
            return markup;
        }

        public void setMarkup(double markup) {
            this.markup = markup;
        }

        public int getWith_serial() {
            return with_serial;
        }

        public void setWith_serial(int with_serial) {
            this.with_serial = with_serial;
        }

        public int getDiscount_exempt() {
            return discount_exempt;
        }

        public void setDiscount_exempt(int discount_exempt) {
            this.discount_exempt = discount_exempt;
        }

        public int getOpen_price() {
            return open_price;
        }

        public void setOpen_price(int open_price) {
            this.open_price = open_price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getMinimum_stock_level() {
            return minimum_stock_level;
        }

        public void setMinimum_stock_level(int minimum_stock_level) {
            this.minimum_stock_level = minimum_stock_level;
        }

        public int getMaximum_stock_level() {
            return maximum_stock_level;
        }

        public void setMaximum_stock_level(int maximum_stock_level) {
            this.maximum_stock_level = maximum_stock_level;
        }

        public double getStock_on_hand() {
            return stock_on_hand;
        }

        public void setStock_on_hand(double stock_on_hand) {
            this.stock_on_hand = stock_on_hand;
        }

        public List<BundleClass> getBundled_items() {
            return bundled_items;
        }

        public void setBundled_items(List<BundleClass> bundled_items) {
            this.bundled_items = bundled_items;
        }

        public int getVat_exempt() {
            return vat_exempt;
        }

        public void setVat_exempt(int vat_exempt) {
            this.vat_exempt = vat_exempt;
        }

        public ItemTypeClass getItem_type() {
            return item_type;
        }

        public void setItem_type(ItemTypeClass item_type) {
            this.item_type = item_type;
        }

        public UOMClass getUom() {
            return uom;
        }

        public void setUom(UOMClass uom) {
            this.uom = uom;
        }

        public List<ItemLocationClass> getItem_locations() {
            return item_locations;
        }

        public void setItem_locations(List<ItemLocationClass> item_locations) {
            this.item_locations = item_locations;
        }

        public class BundleClass {

            private int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

        }

        public class ItemTypeClass {

            private int id;

            private int show_in_cashier;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getShow_in_cashier() {
                return show_in_cashier;
            }

            public void setShow_in_cashier(int show_in_cashier) {
                this.show_in_cashier = show_in_cashier;
            }

        }

        public class UOMClass {

            private int id;

            private String name;

            private String description;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

        }

        public class ItemLocationClass {

            private int id;

            private String name;

            private String status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

        }

    }

}
