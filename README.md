# MoneyIdeasAndroid (GitHub Actions ready)

Demo app Android (Kotlin) — giao diện tiếng Việt, hiển thị danh sách ý tưởng kiếm tiền online, thêm ý tưởng, chia sẻ, đánh dấu đã thử.
Project kèm workflow GitHub Actions để build APK (lưu ý: trước khi chạy workflow trên GitHub, hãy **tạo Gradle wrapper** hoặc push file `gradlew` vào repo — hướng dẫn trong phần 'Chuẩn bị').

## Nội dung
- Mã nguồn Kotlin: `app/src/main/java/com/example/moneyideas/`
- Layout: `app/src/main/res/layout/activity_main.xml`
- Strings: `app/src/main/res/values/strings.xml`
- Workflow: `.github/workflows/android-build.yml`

## Chuẩn bị trước khi push lên GitHub để build tự động
GitHub Actions workflow chạy lệnh `./gradlew :app:assembleRelease`. Để đảm bảo workflow chạy thành công, bạn **phải có Gradle wrapper** (`gradlew`, `gradlew.bat` và thư mục `gradle/` wrapper) trong repo.
Cách dễ nhất:
1. Mở project bằng Android Studio và chọn **File → Settings → Build Tools → Gradle → 'Use default Gradle wrapper (recommended)'** và **Build → Make Project**; Android Studio sẽ tự sinh Gradle wrapper.
2. Hoặc nếu bạn có Gradle trên máy, chạy trong thư mục gốc project:
   ```bash
   gradle wrapper
   ```

## Hướng dẫn nhanh push & build trên GitHub
1. Tạo repo GitHub `hoanghaiqn-127/MoneyIdeasAndroid` (public/private). 
2. `git init` -> `git add .` -> `git commit -m "Initial"` -> `git remote add origin https://github.com/<you>/MoneyIdeasAndroid.git` -> `git push -u origin main`.
3. Vào tab Actions trên GitHub → chọn workflow **Build Android APK** → Run workflow.
4. Chờ build xong → vào phần Artifacts → tải `app-release-unsigned.apk`.

---
Good luck!