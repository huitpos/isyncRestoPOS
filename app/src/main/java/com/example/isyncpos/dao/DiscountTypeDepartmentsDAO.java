package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.isyncpos.entity.DiscountTypeDepartments;

@Dao
public interface DiscountTypeDepartmentsDAO {

    @Insert
    void insert(DiscountTypeDepartments discountTypeDepartments);

    @Query("DELETE FROM discountTypeDepartments WHERE discountTypeId = :discountTypeId")
    Void delete(int discountTypeId);

    @Query("SELECT * FROM discountTypeDepartments WHERE coreId = :departmentId AND discountTypeId = :discountTypeId")
    DiscountTypeDepartments fetchByDepartmentDiscountId(int departmentId, int discountTypeId);

    @Query("SELECT COUNT(*) AS 'totalCount' FROM discountTypeDepartments WHERE discountTypeId = :discountTypeId")
    Long fetchDiscountTypeDepartmentCount(int discountTypeId);

}
