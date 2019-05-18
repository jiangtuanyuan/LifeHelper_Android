package pers.life.helper.view.base;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import pers.life.helper.R;
import pers.life.helper.utils.ActivityCollector;

public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private KProgressHUD kProgressHUD;
    private CompositeDisposable mCompositeDisposable;//订阅


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());
        ButterKnife.bind(this);
        initVariables();
        initViews(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        OkGo.getInstance().cancelTag(this);
        //解除所有未完成的订阅 防止内存泄漏
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
    public CompositeDisposable getmCompositeDisposable() {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        return mCompositeDisposable;
    }

    /**
     * 添加订阅
     *
     * @param disposable
     */
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void initToolbarNav() {
        if (mToolbar == null) {
            try {
                initToolbar();
            } catch (Exception e) {
                return;
            }
        }
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(v -> {
            try {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                onBackPressed();
            } finally {
                onBackPressed();
            }
        });
    }

    /**
     * 加载一个资源图标到Toolbar
     *
     * @param resID
     */
    public void initToolbarIcon(int resID) {
        if (mToolbar == null) {
            try {
                initToolbar();
            } catch (Exception e) {
                return;
            }
        }
        mToolbar.setNavigationIcon(resID);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
    }

    public void setTitle(String title) {
        if (mToolbar == null) {
            initToolbar();
        }
        mToolbar.setTitle(title);
    }

    protected abstract int setLayoutResourceID();

    protected abstract void initVariables(); //上一个界面传过来的数据

    protected abstract void initViews(Bundle savedInstanceState);

    //加载对话框相关
    public KProgressHUD getProgressDialog() {
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
        } else {
            kProgressHUD = new KProgressHUD(this);
        }
        return kProgressHUD;
    }

    public void showProgressDialog(String message) {
        if (kProgressHUD == null) {
            kProgressHUD = new KProgressHUD(this);
        }
        kProgressHUD.setLabel(message);
        if (!isFinishing() && !kProgressHUD.isShowing()) {
            kProgressHUD.show();
        }
    }

    public void showProgressDialog(String message, boolean cancelable) {
        if (kProgressHUD == null) {
            kProgressHUD = new KProgressHUD(this);
        }
        kProgressHUD.setCancellable(cancelable);
        kProgressHUD.setLabel(message);
        if (!isFinishing() && !kProgressHUD.isShowing()) {
            kProgressHUD.show();
        }
    }

    public void closeProgressDialog() {
        if (kProgressHUD != null && kProgressHUD.isShowing()) {
            kProgressHUD.dismiss();
        }
    }
}
