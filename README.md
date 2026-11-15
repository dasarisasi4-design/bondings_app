# BONDINGS 3.0 — native Android + website UI prototype

A dark purple/pink, inclusive connection-discovery prototype. Android is a native Kotlin + Jetpack Compose app; `website/index.html` is a responsive web UI prototype.

## Included in this prototype
- 3-column Android discovery grid with sample photo URLs, online dots, distance labels
- Profile preview dialog and local like toggle
- Discover / Likes / Chat / Premium / Profile navigation
- Filter dialog placeholder
- Plus ₹199 and Premium ₹399 demo plan cards
- Responsive web discovery grid with profile preview and local like toggle
- GitHub Actions workflow to build a debug APK

## Not production-ready yet
This is a UI prototype using sample profiles and remote sample photos. It does **not** yet have real user registration/login, Supabase profile reads/writes, user photo uploads, live location/distance, actual filters, chat backend, moderation/report/block flows, push notifications, subscriptions or Google Play Billing. The plan prices are display-only. Do not submit this prototype to Play as a finished dating/social app.

## Build APK in GitHub Actions
1. Create a GitHub repository and upload the contents of this folder.
2. Push to the `main` branch, or open Actions → **Build BONDINGS APK** → Run workflow.
3. Download the `BONDINGS-3.0-debug-apk` artifact.
4. This is a debug APK for testing only. For Play Store, configure a persistent release signing key securely and build a signed AAB (`bundleRelease`). Never commit a keystore or passwords to the repository.

## Play Store work still required
- Production backend and security/RLS review
- User safety, block/report/moderation and age-gating
- Accurate privacy policy and Data safety declarations matching actual data collection
- Store listing, screenshots, app icon, content rating and target audience declarations
- Signed Android App Bundle, internal/closed testing, and Google review
