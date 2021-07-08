-keep class com.alvarengadev.marketplacelist.data**
-keep class com.alvarengadev.marketplacelist.utils.Constants

-keep class butterknife.** { *; }
 -dontwarn butterknife.internal.**
 -keep class **$$ViewBinder { *; }
 -keepclasseswithmembernames class * {
        @butterknife.* <fields>;
  }
 -keepclasseswithmembernames class * {
        @butterknife.* <methods>;
  }

-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile