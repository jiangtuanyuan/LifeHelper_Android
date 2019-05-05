package pers.life.helper.entity;

/**
 * author : jiang
 * date   : 2019/5/5  22:01
 * desc   :
 */
public class IPEntity {
    private String resultcode;
    private String reason;
    private String error_code;
    private result result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public IPEntity.result getResult() {
        return result;
    }

    public void setResult(IPEntity.result result) {
        this.result = result;
    }

    public class result {
        private String Country;
        private String Province;
        private String City;
        private String Isp;

        public String getCountry() {
            return Country;
        }

        public void setCountry(String country) {
            Country = country;
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

        public String getIsp() {
            return Isp;
        }

        public void setIsp(String isp) {
            Isp = isp;
        }
    }
}
