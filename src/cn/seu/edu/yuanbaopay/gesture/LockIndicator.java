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
 * 手势密码图案提示
 *
 */
public class LockIndicator extends View {
	private int numRow = 3;	// 行
	private int numColum = 3; // 列
	private int patternWidth = 40;
	private int patternHeight = 40;
	private int f = 5;
	private int g = 5;
	private int strokeWidth = 3;
	private Paint paint = null;
	private Drawable patternNoraml = null;
	private Drawable patternPressed = null;
	private String lockPassStr; // 手势密码

//	public LockIndicator(Context paramContext) {
//		super(paramContext);
//	}
//和下面的自定义函数冲突了，但是没有错误额

	public LockIndicator(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet, 0);
		paint = new Paint();
		//图片效果控件，阴影啊，反光啊_(:з」∠)_
		paint.setAntiAlias(true);
		//设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢。设置后会平滑一点_(:з」∠)_
		paint.setStrokeWidth(strokeWidth);
		//当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的粗细度 _(:з」∠)_
		paint.setStyle(Paint.Style.STROKE);
		//设置画笔的样式，为FILL，FILL_OR_STROKE，或STROKE_(:з」∠)_
		patternNoraml = getResources().getDrawable(R.drawable.lock_pattern_node_normal);
		patternPressed = getResources().getDrawable(R.drawable.lock_pattern_node_pressed);
		if (patternPressed != null) {
			patternWidth = patternPressed.getIntrinsicWidth();
			patternHeight = patternPressed.getIntrinsicHeight();
			this.f = (patternWidth / 4);
			this.g = (patternHeight / 4);
			patternPressed.setBounds(0, 0, patternWidth, patternHeight);
			patternNoraml.setBounds(0, 0, patternWidth, patternHeight);
			//移动组件并调整其大小。由 x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小_(:з」∠)_
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//绘制控件视觉界面的画布onDraw(Canvas canvas)
		if ((patternPressed == null) || (patternNoraml == null)) {
			return;
		}
		// 绘制3*3的图标（上面的小图标，记忆每次的手势密码）
		for (int i = 0; i < numRow; i++) {
			for (int j = 0; j < numColum; j++) {
				paint.setColor(-16777216);
				int i1 = j * patternHeight + j * this.g;
				int i2 = i * patternWidth + i * this.f;
				canvas.save();
				//用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。
				canvas.translate(i1, i2);
				//移动原点，默认的原点（0，0）是在屏幕左上角的，你可以通过translate（x，y）把点（x，y）作为原点
				
				//Convas是画图的API_(:з」∠)_
				String curNum = String.valueOf(numColum * i + (j + 1));
				if (!TextUtils.isEmpty(lockPassStr)) {
					if (lockPassStr.indexOf(curNum) == -1) {
						// 未选中
						patternNoraml.draw(canvas);
					} else {
						// 被选中
						patternPressed.draw(canvas);
					}
				} else {
					// 重置状态
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
		
		//效果就是自定义视图的大小为paramInt1,paramInt2，并且放入到ScrollView中，ScrollView会启作用，如果不使用setMeasuredDimension这个方法，那么ScrollView不会有作用。
	}

	/**
	 * 请求重新绘制
	 * @param paramString 手势密码字符序列
	 */
	public void setPath(String paramString) {
		lockPassStr = paramString;
		invalidate();
	}
}