package wu.a.afinaldemo;
/**FinalBitmap 使用方法

加载网络图片就一行代码 fb.display(imageView,url) 

	 * 
	 */
//public class HttpBitmap {
//	private GridView gridView;
//    private FinalBitmap fb;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.images);
//
//        gridView = (GridView) findViewById(R.id.gridView);
//        gridView.setAdapter(mAdapter);
//
//        fb = FinalBitmap.create(this);//初始化FinalBitmap模块
//        fb.configLoadingImage(R.drawable.downloading);
//        //这里可以进行其他十几项的配置，也可以不用配置，配置之后必须调用init()函数,才生效
//        //fb.configBitmapLoadThreadSize(int size)
//        //fb.configBitmapMaxHeight(bitmapHeight)
//    }
//
//
/////////////////////////////adapter getView////////////////////////////////////////////
//
//public View getView(int position, View convertView, ViewGroup parent) {
//    ImageView iv;
//    if(convertView == null){
//        convertView = View.inflate(BitmapCacheActivity.this,R.layout.image_item, null);
//        iv = (ImageView) convertView.findViewById(R.id.imageView);
//        iv.setScaleType(ScaleType.CENTER_CROP);
//        convertView.setTag(iv);
//    }else{
//        iv = (ImageView) convertView.getTag();
//    }
//    //bitmap加载就这一行代码，display还有其他重载，详情查看源码
//    fb.display(iv,Images.imageUrls[position]);
//}
