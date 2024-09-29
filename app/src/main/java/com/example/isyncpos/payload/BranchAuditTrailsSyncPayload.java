package com.example.isyncpos.payload;

public class BranchAuditTrailsSyncPayload {

    private int device_id;
    private int audit_trail_id;
    private int pos_machine_id;
    private int branch_id;
    private int user_id;
    private String user_name;
    private int transaction_id;
    private String action;
    private String description;
    private int authorize_id;
    private String authorize_name;
    private boolean is_sent_to_server;
    private String treg;
    private int order_id;
    private int price_change_reason_id;
    private int company_id;

    public BranchAuditTrailsSyncPayload(int device_id, int audit_trail_id, int pos_machine_id, int branch_id, int user_id, String user_name, int transaction_id, String action, String description, int authorize_id, String authorize_name, boolean is_sent_to_server, String treg, int order_id, int price_change_reason_id, int company_id) {
        this.device_id = device_id;
        this.audit_trail_id = audit_trail_id;
        this.pos_machine_id = pos_machine_id;
        this.branch_id = branch_id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.transaction_id = transaction_id;
        this.action = action;
        this.description = description;
        this.authorize_id = authorize_id;
        this.authorize_name = authorize_name;
        this.is_sent_to_server = is_sent_to_server;
        this.treg = treg;
        this.order_id = order_id;
        this.price_change_reason_id = price_change_reason_id;
        this.company_id = company_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getAudit_trail_id() {
        return audit_trail_id;
    }

    public void setAudit_trail_id(int audit_trail_id) {
        this.audit_trail_id = audit_trail_id;
    }

    public int getPos_machine_id() {
        return pos_machine_id;
    }

    public void setPos_machine_id(int pos_machine_id) {
        this.pos_machine_id = pos_machine_id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAuthorize_id() {
        return authorize_id;
    }

    public void setAuthorize_id(int authorize_id) {
        this.authorize_id = authorize_id;
    }

    public String getAuthorize_name() {
        return authorize_name;
    }

    public void setAuthorize_name(String authorize_name) {
        this.authorize_name = authorize_name;
    }

    public boolean isIs_sent_to_server() {
        return is_sent_to_server;
    }

    public void setIs_sent_to_server(boolean is_sent_to_server) {
        this.is_sent_to_server = is_sent_to_server;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getPrice_change_reason_id() {
        return price_change_reason_id;
    }

    public void setPrice_change_reason_id(int price_change_reason_id) {
        this.price_change_reason_id = price_change_reason_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

}
