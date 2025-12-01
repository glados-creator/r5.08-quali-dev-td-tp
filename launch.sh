GRADLE_OPTS="-Xmx512m" gradle :apps:product-registry-domain-service:quarkusDev
GRADLE_OPTS="-Xmx512m" gradle :apps:product-registry-read-service:quarkusDev
GRADLE_OPTS="-Xmx512m" gradle :apps:store-back:quarkusDev
NODE_OPTIONS='--max-old-space-size=200' pnpm run --filter apps-store-front start
NODE_OPTIONS='--max-old-space-size=200' pnpm run --filter apps-store-front-e2e test