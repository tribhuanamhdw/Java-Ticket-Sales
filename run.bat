@echo off
echo ===================================================
echo Mempersiapkan Sistem Informasi Penjualan Tiket...
echo ===================================================

if not exist "lib" (
    mkdir lib
)

if not exist "lib\mysql-connector-j-8.3.0.jar" (
    echo Mengunduh driver MySQL...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.3.0/mysql-connector-j-8.3.0.jar' -OutFile 'lib\mysql-connector-j-8.3.0.jar'"
    echo Driver berhasil diunduh.
)

if not exist "lib\flatlaf-3.4.1.jar" (
    echo Mengunduh library UI FlatLaf...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/formdev/flatlaf/3.4.1/flatlaf-3.4.1.jar' -OutFile 'lib\flatlaf-3.4.1.jar'"
)

if not exist "out" (
    mkdir out
)

echo.
echo Melakukan kompilasi kode Java...
javac -cp "lib\*" -d out src\main\java\com\ticketsales\model\*.java src\main\java\com\ticketsales\util\*.java src\main\java\com\ticketsales\dao\*.java src\main\java\com\ticketsales\view\*.java src\main\java\com\ticketsales\main\*.java

if %errorlevel% neq 0 (
    echo.
    echo Terjadi kesalahan saat kompilasi. Pastikan JDK sudah terinstal dengan benar.
    pause
    exit /b
)

echo.
echo Menjalankan aplikasi...
echo ===================================================
java -cp "out;lib\*" com.ticketsales.main.Main
pause
