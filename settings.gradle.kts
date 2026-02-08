pluginManagement {
    includeBuild("gradle/build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Woo"
include(":app")
include(":data:model")
include(":data:network")
include(":data:api:woo")
include(":data:woo-products")
include(":data:woo-categories")
include(":domain:woo-products")
include(":feature:product-detail")
include(":feature:home")
include(":domain:woo-categories")
include(":feature:product-list")
include(":core:design-system")
include(":core:ui:common")
include(":feature:category")
include(":feature:cart")
include(":feature:account")
include(":feature:checkout")
include(":feature:orders")
include(":data:database")
include(":data:cart")
include(":domain:cart")
include(":domain:checkout")
include(":data:woo-checkout")
include(":domain:user")
include(":data:woo-user")
include(":data:local")
include(":feature:address")
include(":data:favorite")
include(":domain:favorite")
include(":data:config")
include(":domain:config")
include(":data:woo-orders")
include(":domain:order")
include(":feature:login")
include(":domain:review")
include(":feature:review")
