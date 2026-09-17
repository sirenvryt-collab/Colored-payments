# Donut Pay Color

A tiny Fabric client mod for Minecraft 1.21.1 that recolors Donut SMP payment
chat messages:

- **You paid someone** → the `$amount` shows in **red**
- **Someone paid you** → the `$amount` shows in **green**

It works by matching the text "you paid" / "paid you" in incoming chat
messages, so it should work for messages shaped like:

```
You paid Steve123 $100
Steve123 paid you $250.50
```

If Donut SMP's actual wording is slightly different, tweak the two `Pattern`
lines in `src/main/java/com/donutsmp/paycolor/DonutPayColorClient.java`.

## How to build a .jar using only the GitHub website (no local setup)

1. Create a new **empty** repository on GitHub.
2. On the repo's main page, click **"Add file" → "Upload files"**, then drag
   this entire folder's contents into the browser (make sure the folder
   structure — `src/`, `.github/`, `build.gradle`, etc. — is preserved).
3. Commit the files to the `main` branch.
4. Go to the **Actions** tab of your repo. A workflow called **"Build Mod
   Jar"** will run automatically (it also runs any time you push a change).
5. When it finishes (green checkmark), click into that workflow run, scroll
   to **Artifacts**, and download **donut-pay-color-jar**. Unzip it — inside
   is your `.jar` file.
6. Drop that `.jar` into your Minecraft `mods` folder (you'll also need
   [Fabric Loader](https://fabricmc.net/use/) and
   [Fabric API](https://modrinth.com/mod/fabric-api) installed for Minecraft
   1.21.1).

## Notes

- This is a **client-side only** mod — it doesn't need to be installed on
  the server, only in your own `mods` folder.
- Built for Minecraft **1.21.1** with Fabric. If Donut SMP is on a different
  version, update `minecraft_version`, `yarn_mappings`, `loader_version`,
  and `fabric_version` in `gradle.properties` to match (check
  https://fabricmc.net/develop/ for the right combo), then push again — the
  Action will rebuild automatically.
- If the build fails on GitHub Actions, open the failed run's log — it's
  almost always a version mismatch in `gradle.properties`.
