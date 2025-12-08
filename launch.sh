GRADLE_OPTS="-Xmx512m" gradle :apps:product-registry-domain-service:quarkusDev
GRADLE_OPTS="-Xmx512m" gradle :apps:product-registry-read-service:quarkusDev
GRADLE_OPTS="-Xmx512m" gradle :apps:store-back:quarkusDev

cd apps/store-front
pnpm install .
NODE_OPTIONS='--max-old-space-size=200' pnpm run --filter apps-store-front start

cd apps/store-front-e2e
pnpm install .
pnpm --filter apps-store-front-e2e exec cypress install
NODE_OPTIONS='--max-old-space-size=200' pnpm run --filter apps-store-front-e2e cypress:run