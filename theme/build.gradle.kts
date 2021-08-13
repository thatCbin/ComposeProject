import com.cbin.composeproject.build.dependencies.addComposeOfficialDependencies
import com.cbin.composeproject.build.dependencies.addComposeThirdPartyDependencies
import com.cbin.composeproject.build.dependencies.addCoreAndroidUiDependencies

plugins {
    /**
     * See [common-compose-module-configs-script-plugin.gradle.kts] file
     */
    id("common-compose-module-configs-script-plugin")
}

dependencies {
    addComposeOfficialDependencies()
    addComposeThirdPartyDependencies()
    addCoreAndroidUiDependencies()
}