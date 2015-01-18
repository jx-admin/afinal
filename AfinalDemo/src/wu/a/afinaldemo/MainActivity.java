package wu.a.afinaldemo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.sqlite.ManyToOne;
import net.tsz.afinal.annotation.sqlite.OneToMany;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.db.sqlite.OneToManyLazyLoader;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import net.tsz.afinal.http.HttpHandler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FinalActivity {

	// 无需调用findViewById和setOnclickListener等
	@ViewInject(id = R.id.button, click = "btnClick")
	Button button;
	@ViewInject(id = R.id.textView)
	TextView textView;
	@ViewInject(id = R.id.fhttp_get, click = "btnClick")
	Button fhttp_getfhttp_get;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void btnClick(View v) {
		switch (v.getId()) {
		case R.id.button:
			textView.setText("text set form button");
			break;
		case R.id.fhttp_get:
			getHttp();
			break;
		}
	}

	/**
	 * FinalDB使用方法
	 * 
	 */
	public void createDb() {
		FinalDb db = FinalDb.create(this);
		User user = new User(); // 这里需要注意的是User对象必须有id属性，或者有通过@ID注解的属性
		user.setEmail("mail@tsz.net");
		user.setName("michael yang");
		db.save(user);
	}

	// FinalDB OneToMany懒加载使用方法
	public class Parent {
		private int id;
		@OneToMany(manyColumn = "parentId")
		private OneToManyLazyLoader<Parent, Child> children;
		/* .... */
	}

	public class Child {
		private int id;
		private String text;
		@ManyToOne(column = "parentId")
		private Parent parent;
		/* .... */
	}

	public void db() {
		FinalDb db = FinalDb.create(this);
		List<Parent> all = db.findAll(Parent.class);
		for (Parent item : all) {
			// if(item.getChildren().getList().size()>0)
			// Toast.makeText(this,item.getText() +
			// item.getChildren().getList().get(0).getText(),Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 普通get方法
	 */
	public void getHttp() {
		FinalHttp fh = new FinalHttp();
		fh.get("http://www.baidu.com/", new AjaxCallBack<String>() {
			@Override
			public void onLoading(long count, long current) {
				// 每1秒钟自动被回调一次
				textView.setText(current + "/" + count);
			}

			@Override
			public void onSuccess(String t) {
				textView.setText(t == null ? "null" : t);
			}

			@Override
			public void onStart() {
				// 开始http请求的时候回调
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// 加载失败的时候回调
			}
		});
	}

	/**
	 * 使用FinalHttp上传文件 或者 提交数据 到服务器（post方法）
	 * 
	 * 文件上传到服务器，服务器如何接收
	 * 
	 * @throws FileNotFoundException
	 */
	public void postFile(InputStream inputStream, byte[] bytes)
			throws FileNotFoundException {
		AjaxParams params = new AjaxParams();
		params.put("username", "michael yang");
		params.put("password", "123456");
		params.put("email", "test@tsz.net");
		params.put("profile_picture", new File("/mnt/sdcard/pic.jpg")); // 上传文件
		params.put("profile_picture2", inputStream); // 上传数据流
		params.put("profile_picture3", new ByteArrayInputStream(bytes)); // 提交字节流

		FinalHttp fh = new FinalHttp();
		fh.post("http://www.yangfuhai.com", params, new AjaxCallBack<String>() {
			@Override
			public void onLoading(long count, long current) {
				textView.setText(current + "/" + count);
			}

			@Override
			public void onSuccess(String t) {
				textView.setText(t == null ? "null" : t);
			}
		});
	}

	/**
	 * 使用FinalHttp下载文件： •支持断点续传，随时停止下载任务 或者 开始任务
	 */
	public void httpDownload() {
		FinalHttp fh = new FinalHttp();
		// 调用download方法开始下载
		HttpHandler handler = fh.download("http://www.xxx.com/下载路径/xxx.apk", // 这里是下载的路径
				"/mnt/sdcard/testapk.apk", // 这是保存到本地的路径
				true,// true:断点续传 false:不断点续传（全新下载）
				new AjaxCallBack<File>() {
					@Override
					public void onLoading(long count, long current) {
						textView.setText("下载进度：" + current + "/" + count);
					}

					@Override
					public void onSuccess(File t) {
						textView.setText(t == null ? "null" : t
								.getAbsoluteFile().toString());
					}

				});

		// 调用stop()方法停止下载
		handler.stop();

	}
	
}
