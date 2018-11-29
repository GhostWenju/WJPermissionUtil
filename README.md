# Android运行时请求权限
注意：第一次初始化必须请求你需要的所有权限，之后不必考虑单个或者多个请求
### 使用方法： 
* ###### 1.将添加到项目末尾的根build.gradle中： 
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
}
```
* ###### 2.添加项目依赖： 
```
dependencies {
	        implementation 'com.github.GhostWenju:WJPermissionUtil:v1.0.0'
	}
```
* ###### 3.两行代码请求权限： 
```
String[] permissions = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS};
new WenJuPermissionUtil(this, permissions)
                .setDialogTitle("建议开启所有权限,否则某些功能将无法使用")
                .requestPermisssion();
```
