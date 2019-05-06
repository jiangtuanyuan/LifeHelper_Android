package pers.life.helper.entity;

/**
 * author : jiang
 * date   : 2019/5/6  17:58
 * desc   :
 */
public class PostcodeEntity {
    private String PostNumber;
    private String Province;
    private String City;
    private String District;
    private String Address;

    public String getPostNumber() {
        return PostNumber;
    }

    public void setPostNumber(String postNumber) {
        PostNumber = postNumber;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
