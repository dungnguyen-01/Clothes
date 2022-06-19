package com.clothes.service.cookie;

import com.clothes.utils.ENDE;
import com.clothes.utils.JSON;

import javax.servlet.http.Cookie;
import java.util.List;

public interface CookieService {

    /**
     * Đọc cookie
     * @param name tên cookie
     * @return cookie hoặc null nếu không tồn tại
     */
    Cookie get(String name);

    /**
     * Đọc giá trị cookie
     * @param name tên cookie
     * @return giá trị cookie hoặc "" nếu không tồn tại
     */
    String getValue(String name, String defval);
    /**
     * Tạo và gửi cookie về client
     * @param name tên cookie
     * @param value giá trị cookie
     * @param days số ngày tồn tại
     * @return cookie đã tạo
     */
    Cookie create(String name, String value, Integer days);
    /**
     * Xóa cookie
     * @param name tên cookie
     */
    void delete(String name);


    default Cookie createEncoder(String name, String value, Integer days){
        return this.create(name, ENDE.encode(value),days);
    }

    default String getDecodeValue(String name, String defval){
        return ENDE.decode(this.getValue(name, ENDE.encode(defval)));
    }

    default List<Integer> getVisitIds(){
        String visits = this.getDecodeValue("visits","[]");
        return JSON.pare(visits);
    }

    default  List<Integer> addVisit(Integer id){
        List<Integer> ids = this.getVisitIds();
        if (!ids.contains(id)){
            ids.add(id);
        }
        this.createEncoder("Visits", JSON.stringify(ids),5);
        return ids;
    }
}
