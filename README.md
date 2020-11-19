# WeChat
模拟微信的Android APP  

### 部分规范如下  
##### 1.控件的拓展  
“微信”模块的xml文件就是layout目录下的menu_fragment_1.xml文件  
“通讯录”模块的xml文件就是layout目录下的menu_fragment_2.xml文件  
“发现”模块的xml文件就是layout目录下的menu_fragment_3.xml文件  
“我”模块的xml文件就是layout目录下的menu_fragment_4.xml文件  
如要完善界面控件，只需要在对应的xml文件修改即可  
  

##### 2.使用图片log问题  
Android中的小图片logo均使用**svg**图片，这样才能保证图片不失真，素材可以自行去阿里矢量图下载  
图片logo尺寸：32  
图片logo颜色：可以将微信log截图，然后去这个网站(http://www.jiniannet.com/Page/allcolor)取到颜色  

Android中使用**svg**图片的方式  
1.先将svg图片下载到本地  
2.选中drawable目录，右键->New->Vector Asset,然后Asset Type选择第二个Local file，Name自己取，Path选择图片svg的路径，然后点Next，下一步不用管直接Finish  
3.这样就在drawable目录下创建了svg图片的xml格式，在界面布局文件中直接使用 android:background="@drawable/xxx.xml"就可以了  

