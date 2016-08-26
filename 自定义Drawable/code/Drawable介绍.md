在Android应用中，常常会用到Drawable资源，比如图片资源等，在Android开发中我们是用Drawable类来Drawable类型资源的。
Drawable资源一般存储在应用程序目录的\res\drawable目录下,当然依据分辨率的高低可以分别存储不同分辨率的资源到如下几个目录：
\res\drawable-hdpi
\res\drawable-ldpi
\res\drawable-mdpi
\res\drawable-xdpi
其SDK文档中声明如下：

![](http://images.cnblogs.com/cnblogs_com/jeriffe/201211/201211141709182352.png)

我们看到Drawable是一个抽象类（abstract class），它有好多子类（SubClass）来操作具体类型的资源，比如BitmapDrawable是用来操作位图，ColorDrawable用来操作颜色，
ClipDrawable用来操作剪切板等。
###图片资源
图片资源是简单的Drawable资源，目前Android支持的图片格式有：gif、png、jpg等。我们只需要把图片资源放置到\res\drawable目中，那么在编译后的R.java类中就会生成图片资源的资源ID
我们在程序中就可以通过调用相关的方法来获取图片资源（程序中如果要访问drawable_resource_file_name，那么可以如此：[packagename].R.drawable.drawable_resource_file_name）。
注：Android中drawable中的资源名称有约束，必须是： [a-z0-9_.]（即：只能是字母数字及_和.），而且不能以数字开头，否则编译会报错： Invalid file name: must contain only [a-z0-9_.]
以下代码片段演示了如何访问一个图片资源(资源名称drawablefilename):
ImageView imageView=(ImageView)findViewById(com.jeriffe.app.R.id.ImageView1);
imageView.setImageResource(com.jeriffe.app.R.drawable.drawablefilename);
###StateListDrawable资源
StateListDrawable内可以分配一组Drawable资源，StateListDrawable 被定义在一个XML文件中，以 <selector> 元素起始。其内部的每一个Drawable资源内嵌在<item>元素中。
当StatListDrawable资源作为组件的背景或者前景Drawable资源时，可以随着组件状态的变更而自动切换相对应的资源，例如，一个Button可以处于不同的状态(按钮按下、获取焦点)
我们可以使用一个StateListDrawable资源，来提供不同的背景图片对于每一个状态。，当组件的状态变更时，会自定向下遍历StateListDrawable对应的xml文件来查找第一个匹配的Item。
StatListDrawable资源所支持的组件状态如下图所示：

![](http://images.cnblogs.com/cnblogs_com/jeriffe/201211/201211141709211352.png)

以下代码片段是一个StateListDrawable资源的XML文件描述样例：
XML 文件存储在： res/drawable/button_statelist.xml:


     <?xml version="1.0" encoding="utf-8"?>
     <selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_pressed="true"
          android:drawable="@drawable/button_pressed" /> <!-- pressed -->
    <item android:state_focused="true"
          android:drawable="@drawable/button_focused" /> <!-- focused -->
    <item android:state_hovered="true"
          android:drawable="@drawable/button_focused" /> <!-- hovered -->
     </selector>
以下是Button的Layout文件：

    <Button
    android:layout_height="wrap_content"
    android:layout_width="wrap_content"
    android:background="@drawable/button" />
当然我们也可以通过代码来设置Button的背景图片：

    Button imageButton=(Button)findViewById(R.id.imageButton);
    imageButton.setBackgroundResourc(com.jeriffe.app.R.drawable.button_statelist);
###ShapeDrawable资源
ShapeDrawable资源绘制一个特定的形状，比如矩形、椭圆等。如果你想自己动态的绘制二位图形，那么我们就可以使用ShapeDrawable资源对象，用ShapeDrawable,我们可以绘制我们所能想象的形状。。一个ShapeDrawable 需要一个Shape对象来管理呈现资源到UI Screen,如果没有Shape设置，那么会默认使用RectShape对象。
ShapeDrawable 被定义在一个XML文件中，以 <shape> 元素起始。其内部的每一个Drawable资源内嵌在<item>元素中
以下代码片段便是一个ShapeDrawable的XML定义：

     <?xml version="1.0" encoding="UTF-8"?>
    <shape xmlns:android="http://schemas.android.com/apk/res/android" 
    android:shape="oval">
    <!-- 定义填充渐变颜色 -->
    <gradient 
        android:startColor="#00f" 
        android:endColor="#00f" 
        android:angle="45"
        android:type="sweep"/> 
    <!-- 设置内填充 -->
    <padding android:left="7dp" 
        android:top="7dp" 
        android:right="7dp" 
        android:bottom="7dp" />
    <!-- 设置圆角矩形 -->
    <corners android:radius="8dp" /> 
    </shape>
我们可以用ShapeDrawable 来设置组件的背景色（用setBackgroundDrawable()方法），如上的代码片段可设置一个TextEdit的背景色为蓝色的椭圆形状。当然我们也可以绘制自定义的View
我们构建自定义形状的View时，由于ShapeDrawable 有其自己的draw()方法，我们可以构建一个View视图的子类，然后override View.onDraw()方法，如下代码片段是一个样例：
     public class CustomDrawableView extends View {
      private ShapeDrawable mDrawable;

      public CustomDrawableView(Context context) {
      super(context);

      int x = 10;
      int y = 10;
      int width = 300;
      int height = 50;

      mDrawable = new ShapeDrawable(new OvalShape());
      mDrawable.getPaint().setColor(0xff74AC23);
      mDrawable.setBounds(x, y, x + width, y + height);
      }

      protected void onDraw(Canvas canvas) {
      mDrawable.draw(canvas);
      }
    }
基于上述代码我们可以在我们的Activity中编程的构建自定义视图：
CustomDrawableView mCustomDrawableView;

      protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mCustomDrawableView = new CustomDrawableView(this);

      setContentView(mCustomDrawableView);
      }
当然我们也可以使用XML文件来描述：自定义的Drawable类必须重载view (Context, AttributeSet) 构造函数。接着我们添加Layout文件如下：
 
      <com.example.shapedrawable.CustomDrawableView
      android:layout_width="fill_parent"
      android:layout_height="wrap_content"
      />
  
###ClipDrawable资源
ClipDrawable资源定义在一个XML中，表示裁剪(Clips)一个其他资源基于ClipDrawable资源的Level。你可以控制裁剪的Drawable的宽度高度及gravity属性，ClipDrawable常常被用来作为一个progressbars的实现。
以下样例是一个ClipDrawable资源：

     <?xml version="1.0" encoding="utf-8"?>
     <clip xmlns:android="http://schemas.android.com/apk/res/android"
     android:drawable="@drawable/android"
     android:clipOrientation="horizontal"
     android:gravity="left" />

下面的ImageView布局文件应用Clipdrawable资源：
 
    <ImageView
    android:id="@+id/image"
	android:background="@drawable/clip"
	android:layout_height="wrap_content"
	android:layout_width="wrap_content" />

下面的代码获取drawable并且增加其裁剪，以便于渐增的显示图像

	ImageView imageview = (ImageView) findViewById(R.id.image);
	ClipDrawable drawable = (ClipDrawable) imageview.getDrawable();
	drawable.setLevel(drawable.getLevel() + 1000);

当然我们可以使用一个Timer来实现图片的渐增显示。
clip_image0014_thumb_thumb
注意: 默认的Level值是0，表示图片被这个裁剪，故图片是不可见的。当值达到10000是代码裁剪为0，图片可以完全显示。


###AnimationDrawable
AnimationDrawable通过定义一系列的Drawable对象构建一个基于帧的动画(frame-by-frame animations)，可以被用来作为视图的背景色。
最简单的构建一个帧动画是在XML文件中构建一个动画,我们可以设定动画作为视图的背景色，通过调用AnimationDrawable.start()方法来运行动画。
如下代码片段是一个AnimationDrawable资源的XML文件,资源文件位置：res\drawable\spin_animation.xml

	<animation-list xmlns:android="http://schemas.android.com/apk/res/android"
    android:oneshot="true">
    <item android:drawable="@drawable/rocket_thrust1" android:duration="200" />
    <item android:drawable="@drawable/rocket_thrust2" android:duration="200" />
    <item android:drawable="@drawable/rocket_thrust3" android:duration="200" />
	</animation-list>
我们可以看到，AnimationDrawable资源文件以<animation-list>元素为根，包含一系列的<Item>节点，每一个节点定义了一个帧(frame)及持续时常。
上述动画运行了3个帧，通过设置android:oneshot 属性(attribute)为true，动画会循环一次并停留在最后一帧，如果为false那么会轮询(loop)的运行动画
我们可以通过编码来加载播放动画：
 	 
 	 // Load the ImageView that will host the animation and
	 // set its background to our AnimationDrawable XML resource.
 	ImageView img = (ImageView)findViewById(R.id.spinning_wheel_image);
 	img.setBackgroundResource(R.drawable.spin_animation);

 	// Get the background, which has been compiled to an AnimationDrawable object.
	 AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

 	// Start the animation (looped playback by default).
 	frameAnimation.start();
 
注意：AnimationDrawable. start()方法不能够在Activity的onCreate()方法中调用，因为AnimationDrawable还未完全的附加(attached)到Window,如果你不需要交互而立即播放动画，那么可以在onWindowFocusChanged() 方法中，这个方法会在你的Activity Windows获取焦点是触发。