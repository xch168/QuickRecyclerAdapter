# QuickRecyclerAdapter

## Download
```groovy
compile 'com.github.xch168:quick-recycler-adapter:0.0.2'
```

## Usage
```java
public class GankAdapter extends QuickAdapter<Gank> {

    public GankAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.repo_item;
    }

    @Override
    protected int getEmptyViewLayoutId() {
        return R.layout.quick_empty_view;
    }

    @Override
    protected void convert(QuickViewHolder holder, Gank item) {

        holder.setText(R.id.tv, item.desc);
    }
}
```
