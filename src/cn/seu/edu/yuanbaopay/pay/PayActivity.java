package cn.seu.edu.yuanbaopay.pay;

import java.util.Hashtable;

import cn.seu.edu.yuanbaopay.gesture.GestureVerifyActivity;
import cn.seu.edu.yuanbaopay.main.LoginActivity;
import cn.seu.edu.yuanbaopay.main.RegisterActivity;
import cn.seu.edu.yuanbaopay.redpacket.RedPacketLuckyActivity;
import cn.seu.edu.yuanbaopay.cameraQR.CaptureActivity;

import com.example.yuanbaopay.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PayActivity extends Activity implements OnClickListener {
	private TextView tvTitle;
	private TextView tvBack;
	private ImageView tvQRcode;
	private ImageView tverQR;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	int QR_WIDTH=500;
	int QR_HEIGHT=500;
	String url="apple/8.5/孟嘉琪/2015.9.14";
	String goodinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent i=getIntent();
		goodinfo=i.getStringExtra("goodinfo");
		setContentView(R.layout.activity_pay);
		initView();	
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("付款");
		tvBack = (TextView) findViewById(R.id.title_back);
		tvBack.setOnClickListener(this);
		tvQRcode = (ImageView)findViewById(R.id.img_pay_toQRpaycode);
		tvQRcode.setOnClickListener(this);
		tverQR = (ImageView)findViewById(R.id.img_pay_toerQR);
		tverQR.setOnClickListener(this);
		
	}
	
	public void createQRImage(String url)
    {
        try
        {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1)
            {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE,QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++)
            {
                for (int x = 0; x < QR_WIDTH; x++)
                {
                    if (bitMatrix.get(x, y))
                    {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    }
                    else
                    {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            ImageView iv = (ImageView) findViewById(R.id.ercode_pay);
            iv.setImageBitmap(bitmap);
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			pref=PreferenceManager.getDefaultSharedPreferences(PayActivity.this);
			editor=pref.edit();
			editor.putString("paytype", "付款");
			editor.putString("payname","好邻居超市");
			editor.putString("paymonmey", goodinfo);
			editor.commit();
			finish();
			break;
		case R.id.img_pay_toQRpaycode:
			createQRImage(url);
			break;
		case R.id.img_pay_toerQR:
			Intent i_erQR = new Intent(PayActivity.this,
					CaptureActivity.class);
			startActivity(i_erQR);
			break;
		default:
			break;
		}
	}

}
