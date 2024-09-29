package com.example.isyncpos.response.entity;

public class CompanyInformationResponse {

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

        private int id, client_id, barangay_id, city_id, province_id, region_id;

        private String status, company_registered_name, company_name, trade_name, logo, country, phone_number, street, slug, unit_floor_number, pos_type;

        private RegionClass region;

        private ProvinceClass province;

        private CityClass city;

        private BarangayClass barangay;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
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

        public RegionClass getRegion() {
            return region;
        }

        public void setRegion(RegionClass region) {
            this.region = region;
        }

        public ProvinceClass getProvince() {
            return province;
        }

        public void setProvince(ProvinceClass province) {
            this.province = province;
        }

        public CityClass getCity() {
            return city;
        }

        public void setCity(CityClass city) {
            this.city = city;
        }

        public BarangayClass getBarangay() {
            return barangay;
        }

        public void setBarangay(BarangayClass barangay) {
            this.barangay = barangay;
        }

        public class RegionClass {

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

        public class ProvinceClass {

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

        public class CityClass {

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

        public class BarangayClass {

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

}
