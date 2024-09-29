package com.example.isyncpos.response.entity;

public class BranchInformationResponse {

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

        private int id, company_id, cluster_id, region_id, province_id, city_id, barangay_id;

        private String status, name, code, street, unit_floor_number, slug, phone_number, receipt_header, receipt_bottom_text;

        private RegionClass region;

        private ProvinceClass province;

        private CityClass city;

        private BarangayClass barangay;

        private ClusterClass cluster;

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

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getUnit_floor_number() {
            return unit_floor_number;
        }

        public void setUnit_floor_number(String unit_floor_number) {
            this.unit_floor_number = unit_floor_number;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
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

        public ClusterClass getCluster() {
            return cluster;
        }

        public void setCluster(ClusterClass cluster) {
            this.cluster = cluster;
        }

        public class ClusterClass {

            private int id, company_id;

            private String name, description, status;

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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

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
