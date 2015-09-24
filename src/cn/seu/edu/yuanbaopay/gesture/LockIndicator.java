package cn.seu.edu.yuanbaopay.gesture;

import com.example.yuanbaopay.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 
 * ��������ͼ����ʾ
 *
 */
public class LockIndicator extends View {
	private int numRow = 3;	// ��
	private int numColum = 3; // ��
	private int patternWidth = 40;
	private int patternHeight = 40;
	private int f = 5;
	private int g = 5;
	private int strokeWidth = 3;
	private Paint paint = null;
	private Drawable patternNoraml = null;
	private Drawable patternPressed = null;
	private String lockPassStr; // ��������

//	public LockIndicator(Context paramContext) {
//		super(paramContext);
//	}
//��������Զ��庯����ͻ�ˣ�����û�д����

	public LockIndicator(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet, 0);
		paint = new Paint();
		//ͼƬЧ���ؼ�����Ӱ�������Ⱑ_(:�١���)_
		paint.setAntiAlias(true);
		//�����Ƿ�ʹ�ÿ���ݹ��ܣ������Ľϴ���Դ������ͼ���ٶȻ���������ú��ƽ��һ��_(:�١���)_
		paint.setStrokeWidth(strokeWidth);
		//��������ʽΪSTROKE��FILL_OR_STROKEʱ�����ñ�ˢ�Ĵ�ϸ�� _(:�١���)_
		paint.setStyle(Paint.Style.STROKE);
		//���û��ʵ���ʽ��ΪFILL��FILL_OR_STROKE����STROKE_(:�١���)_
		patternNoraml = getResources().getDrawable(R.drawable.lock_pattern_node_normal);
		patternPressed = getResources().getDrawable(R.drawable.lock_pattern_node_pressed);
		if (patternPressed != null) {
			patternWidth = patternPressed.getIntrinsicWidth();
			patternHeight = patternPressed.getIntrinsicHeight();
			this.f = (patternWidth / 4);
			this.g = (patternHeight / 4);
			patternPressed.setBounds(0, 0, patternWidth, patternHeight);
			patternNoraml.setBounds(0, 0, patternWidth, patternHeight);
			//�ƶ�������������С���� x �� y ָ�����Ͻǵ���λ�ã��� width �� height ָ���µĴ�С_(:�١���)_
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//���ƿؼ��Ӿ�����Ļ���onDraw(Canvas canvas)
		if ((patternPressed == null) || (patternNoraml == null)) {
			return;
		}
		// ����3*3��ͼ�꣨�����Сͼ�꣬����ÿ�ε��������룩
		for (int i = 0; i < numRow; i++) {
			for (int j = 0; j < numColum; j++) {
				paint.setColor(-16777216);
				int i1 = j * patternHeight + j * this.g;
				int i2 = i * patternWidth + i * this.f;
				canvas.save();
				//��������Canvas��״̬��save֮�󣬿��Ե���Canvas��ƽ�ơ���������ת�����С��ü��Ȳ�����
				canvas.translate(i1, i2);
				//�ƶ�ԭ�㣬Ĭ�ϵ�ԭ�㣨0��0��������Ļ���Ͻǵģ������ͨ��translate��x��y���ѵ㣨x��y����Ϊԭ��
				
				//Convas�ǻ�ͼ��API_(:�١���)_
				String curNum = String.valueOf(numColum * i + (j + 1));
				if (!TextUtils.isEmpty(lockPassStr)) {
					if (lockPassStr.indexOf(curNum) == -1) {
						// δѡ��
						patternNoraml.draw(canvas);
					} else {
						// ��ѡ��
						patternPressed.draw(canvas);
					}
				} else {
					// ����״̬
					patternNoraml.draw(canvas);
				}
				canvas.restore();
			}
		}
	}

	@Override
	protected void onMeasure(int paramInt1, int paramInt2) {
		if (patternPressed != null)
			setMeasuredDimension(numColum * patternHeight + this.g
					* (-1 + numColum), numRow * patternWidth + this.f
					* (-1 + numRow));
		
		//Ч�������Զ�����ͼ�Ĵ�СΪparamInt1,paramInt2�����ҷ��뵽ScrollView�У�ScrollView�������ã������ʹ��setMeasuredDimension�����������ôScrollView���������á�
	}

	/**
	 * �������»���
	 * @param paramString ���������ַ�����
	 */
	public void setPath(String paramString) {
		lockPassStr = paramString;
		invalidate();
	}
}