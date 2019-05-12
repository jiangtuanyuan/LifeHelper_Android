package pers.life.helper.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : jiang
 * date   : 2019/5/12  11:53
 * desc   :
 */
public class RecipeEntity implements Serializable {
    /**
     * id : 46662
     * title : 小炒茄子
     * tags : 家常菜;健脾开胃;延缓衰老;高血压;高血脂;运动员;春;夏;原味;微辣;10分钟内;炒;简单;香;青少年;白领;夏季;降血脂;降血压;鲜香;口味菜;全菜系;1-2人;降低胆固醇;炒锅;冬平和质
     * imtro : 喜欢吃茄子，具说吃茄子不削皮更好，因为营养全在茄子皮里面，可还是习惯把它削削呵呵...
     * ingredients : 茄子,120g;青辣椒,10g;红辣椒,10g
     * burden : 蒜,5g;葱,适量;盐,适量;生抽,适量;鸡粉,适量;花生油,适量
     * albums : ["http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/t/47/46662_137898.jpg"]
     * steps : [{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/467/46662_319fb584018b4c28.jpg","step":"1.茄子花刀削皮切成条入清水里浸泡待用。"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/467/46662_1a30db0e0b734e51.jpg","step":"2.青红椒切粗丝，蒜切粒，葱切花。"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/467/46662_d92d4178331f0fa2.jpg","step":"3.锅里放花生油爆香蒜粒。"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/467/46662_8fd7987ba846cf9a.jpg","step":"4.放入茄子大火翻炒至茄子软身。"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/467/46662_a675475e988b26fe.jpg","step":"5.放入青红椒丝，翻炒至断生。"},{"img":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/467/46662_f84ccbd4bfb72588.jpg","step":"6.放少量的清水，调入盐，生抽，鸡粉，葱花炒匀即可上碟。"}]
     */
    private String id;
    private String title;
    private String tags;
    private String imtro;
    private String ingredients;
    private String burden;
    private List<String> albums;
    private List<StepsBean> steps;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImtro() {
        return imtro;
    }

    public void setImtro(String imtro) {
        this.imtro = imtro;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getBurden() {
        return burden;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public List<String> getAlbums() {
        return albums;
    }

    public void setAlbums(List<String> albums) {
        this.albums = albums;
    }

    public List<StepsBean> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsBean> steps) {
        this.steps = steps;
    }

    public static class StepsBean implements Serializable {
        /**
         * img : http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/s/467/46662_319fb584018b4c28.jpg
         * step : 1.茄子花刀削皮切成条入清水里浸泡待用。
         */

        private String img;
        private String step;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }
    }
}
