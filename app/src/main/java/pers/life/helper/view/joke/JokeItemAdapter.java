package pers.life.helper.view.joke;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Random;

import pers.life.helper.R;
import pers.life.helper.entity.JokeEntity;

public class JokeItemAdapter extends BaseItemDraggableAdapter<JokeEntity, BaseViewHolder> {
    public JokeItemAdapter(List<JokeEntity> data) {
        super(R.layout.joke_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeEntity item) {
        Random random = new Random();
        int ranColor = 0xff000000 | random.nextInt(0x00ffffff);
        helper.setText(R.id.tv_content, item.getContent())
                .setTextColor(R.id.tv_content, ranColor);

    }
}
