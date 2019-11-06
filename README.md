# DishOrderApp

![APP图标][1]

## 预览 ##

| 点菜界面1  | 点菜界面2 | 支付订单  |
|:------------------------------:|:---------------------------------:|:--------------------------------:|
|<img src="https://raw.githubusercontent.com/AhaYujie/DishOrderApp/master/images/点菜界面1.jpg" width="200" hegiht="700"/> | <img src="https://raw.githubusercontent.com/AhaYujie/DishOrderApp/master/images/点菜界面2.jpg" width="200" hegiht="700"/> | <img src="https://raw.githubusercontent.com/AhaYujie/DishOrderApp/master/images/支付订单.jpg" width="200" hegiht="700"/>|

| 历史订单  | 订单详情 | 删除订单  |
|:------------------------------:|:---------------------------------:|:--------------------------------:|
|<img src="https://raw.githubusercontent.com/AhaYujie/DishOrderApp/master/images/历史订单.jpg" width="200" hegiht="700"/> | <img src="https://raw.githubusercontent.com/AhaYujie/DishOrderApp/master/images/订单详情.jpg" width="200" hegiht="700"/> | <img src="https://raw.githubusercontent.com/AhaYujie/DishOrderApp/master/images/删除订单.jpg" width="200" hegiht="700"/>|

## 系统架构 ##

DishOrderApp采用了MVVM架构，用[MVVMHabit][2]框架实现。下面是项目的大体架构设计图。


<img src="https://raw.githubusercontent.com/AhaYujie/DishOrderApp/master/images/架构设计图.jpg" width="600" hegiht="400"/>


 - 顶部深蓝色层是控制UI的View层，就是Android里的Activity和Fragment这些。
 - 绿色的是ViewModel层，负责持有菜和订单的数据以及和DataRepository的通信。当ViewModel层持有的数据发生变化时，通过数据绑定，View层会自动更新UI。
 - 黄色的是Model层，负责数据的存储和读取。调用者ViewModel不用关心数据的来源是本地数据库还是服务器，由DataRepository来决定是从本地数据库SQLite还是从服务器获取数据。
 - 红色的是菜和订单的数据源。
 - 灰色SQlite表示本地数据层，Dao表示访问数据的对象，采用LitePal框架进行数据持久化处理。
 - 紫色Service表示网络数据层，使用OKHttp+Retrofit+RxJava与服务器进行网络通信。

架构图里面的箭头表示对象之间的引用。比如OrderDishFragment对象持有OrderDishViewModel对象的引用，而OrderDishViewModel对象不能反过来持有OrderDishFragment对象的引用。以及对象之间的引用不能跨层，比如负责UI的View层只能持有ViewModel层，而不能持有Model层。

## 使用 ##

APK：[下载链接][4]

**注意**：因为缺少服务器的配置文件`server_config.properties`，下载此项目代码构建APP是不可以的。若想自己构建APP，需要实现服务器接口，[接口文档][5]，并在`/app/app/src/main/assets/`目录里面添加`server_config.properties`文件，该文件里面有 `token` 和 `server_route` 两个属性。
    


  [1]: https://raw.githubusercontent.com/AhaYujie/DishOrderApp/master/app/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png
  [2]: https://github.com/goldze/MVVMHabit
  [4]: https://www.pgyer.com/O35V
  [5]: https://github.com/xilou31/Dishes-OrderingSystem/blob/master/API%E6%8E%A5%E5%8F%A3/%E6%8E%A5%E5%8F%A3%E6%96%87%E6%A1%A3.md
