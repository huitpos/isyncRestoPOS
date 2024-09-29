package com.example.isyncpos.response.entity;

public class ActivateMachineResponse {

    private boolean success;
    private DataClass data;
    private String message;
    private String code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataClass getData() {
        return data;
    }

    public void setData(DataClass data) {
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

        private int id, branch_id, limit_amount, vat, or_counter, x_reading_counter, z_reading_counter, void_counter;
        private BranchClass branch;
        private String machine_number, serial_number, min, receipt_header, receipt_bottom_text, permit_number, accreditation_number, valid_from, valid_to, tin, status, product_key, device, type;
        private DeviceClass device_info;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }

        public int getLimit_amount() {
            return limit_amount;
        }

        public void setLimit_amount(int limit_amount) {
            this.limit_amount = limit_amount;
        }

        public int getVat() {
            return vat;
        }

        public void setVat(int vat) {
            this.vat = vat;
        }

        public BranchClass getBranch() {
            return branch;
        }
        public void setBranch(BranchClass branch) {
            this.branch = branch;
        }

        public String getMachine_number() {
            return machine_number;
        }

        public void setMachine_number(String machine_number) {
            this.machine_number = machine_number;
        }

        public String getSerial_number() {
            return serial_number;
        }

        public void setSerial_number(String serial_number) {
            this.serial_number = serial_number;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getReceipt_header() {
            return receipt_header;
        }

        public void setReceipt_header(String receipt_header) {
            this.receipt_header = receipt_header;
        }

        public String getReceipt_bottom_text() {
            return receipt_bottom_text;
        }

        public void setReceipt_bottom_text(String receipt_bottom_text) {
            this.receipt_bottom_text = receipt_bottom_text;
        }

        public String getPermit_number() {
            return permit_number;
        }

        public void setPermit_number(String permit_number) {
            this.permit_number = permit_number;
        }

        public String getAccreditation_number() {
            return accreditation_number;
        }

        public void setAccreditation_number(String accreditation_number) {
            this.accreditation_number = accreditation_number;
        }

        public String getValid_from() {
            return valid_from;
        }

        public void setValid_from(String valid_from) {
            this.valid_from = valid_from;
        }

        public String getValid_to() {
            return valid_to;
        }

        public void setValid_to(String valid_to) {
            this.valid_to = valid_to;
        }

        public String getTin() {
            return tin;
        }

        public void setTin(String tin) {
            this.tin = tin;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProduct_key() {
            return product_key;
        }

        public void setProduct_key(String product_key) {
            this.product_key = product_key;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public DeviceClass getDevice_info() {
            return device_info;
        }

        public void setDevice_info(DeviceClass device_info) {
            this.device_info = device_info;
        }

        public int getOr_counter() {
            return or_counter;
        }

        public void setOr_counter(int or_counter) {
            this.or_counter = or_counter;
        }

        public int getX_reading_counter() {
            return x_reading_counter;
        }

        public void setX_reading_counter(int x_reading_counter) {
            this.x_reading_counter = x_reading_counter;
        }

        public int getZ_reading_counter() {
            return z_reading_counter;
        }

        public void setZ_reading_counter(int z_reading_counter) {
            this.z_reading_counter = z_reading_counter;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getVoid_counter() {
            return void_counter;
        }

        public void setVoid_counter(int void_counter) {
            this.void_counter = void_counter;
        }

        public class BranchClass {

            private int id, company_id, cluster_id, region_id, province_id, city_id, barangay_id;
            private BranchCompanyClass company;
            private BranchClusterClass cluster;
            private BranchRegionClass region;
            private BranchProvinceClass province;
            private BranchCityClass city;
            private BranchBarangayClass barangay;
            private String status, name, code, location, unit_number, floor_number, street, zip, slug, pos_type, phone_number;

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

            public int getCluster_id() {
                return cluster_id;
            }

            public void setCluster_id(int cluster_id) {
                this.cluster_id = cluster_id;
            }

            public int getRegion_id() {
                return region_id;
            }

            public void setRegion_id(int region_id) {
                this.region_id = region_id;
            }

            public int getProvince_id() {
                return province_id;
            }

            public void setProvince_id(int province_id) {
                this.province_id = province_id;
            }

            public int getCity_id() {
                return city_id;
            }

            public void setCity_id(int city_id) {
                this.city_id = city_id;
            }

            public int getBarangay_id() {
                return barangay_id;
            }

            public void setBarangay_id(int barangay_id) {
                this.barangay_id = barangay_id;
            }

            public BranchCompanyClass getCompany() {
                return company;
            }

            public void setCompany(BranchCompanyClass company) {
                this.company = company;
            }

            public BranchClusterClass getCluster() {
                return cluster;
            }

            public void setCluster(BranchClusterClass cluster) {
                this.cluster = cluster;
            }

            public BranchRegionClass getRegion() {
                return region;
            }

            public void setRegion(BranchRegionClass region) {
                this.region = region;
            }

            public BranchProvinceClass getProvince() {
                return province;
            }

            public void setProvince(BranchProvinceClass province) {
                this.province = province;
            }

            public BranchCityClass getCity() {
                return city;
            }

            public void setCity(BranchCityClass city) {
                this.city = city;
            }

            public BranchBarangayClass getBarangay() {
                return barangay;
            }

            public void setBarangay(BranchBarangayClass barangay) {
                this.barangay = barangay;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getUnit_number() {
                return unit_number;
            }

            public void setUnit_number(String unit_number) {
                this.unit_number = unit_number;
            }

            public String getFloor_number() {
                return floor_number;
            }

            public void setFloor_number(String floor_number) {
                this.floor_number = floor_number;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getZip() {
                return zip;
            }

            public void setZip(String zip) {
                this.zip = zip;
            }

            public String getSlug() {
                return slug;
            }

            public void setSlug(String slug) {
                this.slug = slug;
            }

            public String getPos_type() {
                return pos_type;
            }

            public void setPos_type(String pos_type) {
                this.pos_type = pos_type;
            }

            public String getPhone_number() {
                return phone_number;
            }

            public void setPhone_number(String phone_number) {
                this.phone_number = phone_number;
            }

            public class BranchCompanyClass {

                private int id, client_id, barangay_id, city_id, province_id, region_id;
                private CompanyRegionClass region;
                private CompanyProvinceClass province;
                private CompanyCityClass city;
                private CompanyBarangayClass barangay;
                private String company_registered_name, company_name, trade_name, logo, country, phone_number, slug, unit_floor_number, pos_type;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getClient_id() {
                    return client_id;
                }

                public void setClient_id(int client_id) {
                    this.client_id = client_id;
                }

                public int getBarangay_id() {
                    return barangay_id;
                }

                public void setBarangay_id(int barangay_id) {
                    this.barangay_id = barangay_id;
                }

                public int getCity_id() {
                    return city_id;
                }

                public void setCity_id(int city_id) {
                    this.city_id = city_id;
                }

                public int getProvince_id() {
                    return province_id;
                }

                public void setProvince_id(int province_id) {
                    this.province_id = province_id;
                }

                public int getRegion_id() {
                    return region_id;
                }

                public void setRegion_id(int region_id) {
                    this.region_id = region_id;
                }

                public CompanyRegionClass getRegion() {
                    return region;
                }

                public void setRegion(CompanyRegionClass region) {
                    this.region = region;
                }

                public CompanyProvinceClass getProvince() {
                    return province;
                }

                public void setProvince(CompanyProvinceClass province) {
                    this.province = province;
                }

                public CompanyCityClass getCity() {
                    return city;
                }

                public void setCity(CompanyCityClass city) {
                    this.city = city;
                }

                public CompanyBarangayClass getBarangay() {
                    return barangay;
                }

                public void setBarangay(CompanyBarangayClass barangay) {
                    this.barangay = barangay;
                }

                public String getCompany_registered_name() {
                    return company_registered_name;
                }

                public void setCompany_registered_name(String company_registered_name) {
                    this.company_registered_name = company_registered_name;
                }

                public String getCompany_name() {
                    return company_name;
                }

                public void setCompany_name(String company_name) {
                    this.company_name = company_name;
                }

                public String getTrade_name() {
                    return trade_name;
                }

                public void setTrade_name(String trade_name) {
                    this.trade_name = trade_name;
                }

                public String getLogo() {
                    return logo;
                }

                public void setLogo(String logo) {
                    this.logo = logo;
                }

                public String getCountry() {
                    return country;
                }

                public void setCountry(String country) {
                    this.country = country;
                }

                public String getPhone_number() {
                    return phone_number;
                }

                public void setPhone_number(String phone_number) {
                    this.phone_number = phone_number;
                }

                public String getSlug() {
                    return slug;
                }

                public void setSlug(String slug) {
                    this.slug = slug;
                }

                public String getUnit_floor_number() {
                    return unit_floor_number;
                }

                public void setUnit_floor_number(String unit_floor_number) {
                    this.unit_floor_number = unit_floor_number;
                }

                public String getPos_type() {
                    return pos_type;
                }

                public void setPos_type(String pos_type) {
                    this.pos_type = pos_type;
                }

                public class CompanyRegionClass {

                    private int id;
                    private String name;

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

                }

                public class CompanyProvinceClass {

                    private int id, region_id;
                    private String name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public int getRegion_id() {
                        return region_id;
                    }

                    public void setRegion_id(int region_id) {
                        this.region_id = region_id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                }

                public class CompanyCityClass {

                    private int id, province_id;
                    private String name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public int getProvince_id() {
                        return province_id;
                    }

                    public void setProvince_id(int province_id) {
                        this.province_id = province_id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                }

                public class CompanyBarangayClass {

                    private int id, city_id;
                    private String name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public int getCity_id() {
                        return city_id;
                    }

                    public void setCity_id(int city_id) {
                        this.city_id = city_id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                }

            }

            public class BranchClusterClass {

                private int id, company_id;

                private String name, description;

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

            public class BranchRegionClass {

                private int id;
                private String name;

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

            }

            public class BranchProvinceClass {

                private int id, region_id;
                private String name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getRegion_id() {
                    return region_id;
                }

                public void setRegion_id(int region_id) {
                    this.region_id = region_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

            }

            public class BranchCityClass {

                private int id, province_id;
                private String name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getProvince_id() {
                    return province_id;
                }

                public void setProvince_id(int province_id) {
                    this.province_id = province_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

            }

            public class BranchBarangayClass {

                private int id, city_id;
                private String name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getCity_id() {
                    return city_id;
                }

                public void setCity_id(int city_id) {
                    this.city_id = city_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

            }

        }

        public class DeviceClass {

            private int id, pos_machine_id;

            private String serial, model, android_id, manufacturer, board, status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPos_machine_id() {
                return pos_machine_id;
            }

            public void setPos_machine_id(int pos_machine_id) {
                this.pos_machine_id = pos_machine_id;
            }

            public String getSerial() {
                return serial;
            }

            public void setSerial(String serial) {
                this.serial = serial;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getAndroid_id() {
                return android_id;
            }

            public void setAndroid_id(String android_id) {
                this.android_id = android_id;
            }

            public String getManufacturer() {
                return manufacturer;
            }

            public void setManufacturer(String manufacturer) {
                this.manufacturer = manufacturer;
            }

            public String getBoard() {
                return board;
            }

            public void setBoard(String board) {
                this.board = board;
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
