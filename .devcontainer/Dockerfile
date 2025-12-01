FROM debian:trixie-slim

ENV SDKMAN_JDK_VERSION=21-tem
ENV SDKMAN_GRADLE_VERSION=
ENV SDKMAN_QUARKUS_VERSION=
ENV NODE_MAJOR_VERSION=24
ENV PNPM_VERSION=10
ENV NODE_OPTIONS='--max-old-space-size=200'
ENV GRADLE_OPTS="-Xmx512m"

RUN apt update
RUN apt install -y git curl wget unzip zip libxtst6 graphviz libxi6 nodejs gcc zlib1g-dev libgtk-3-0 libgbm-dev libnotify-dev libnss3 libxss1 libasound2 xauth xvfb chromium

workdir /install
## Install Docker CE Cli
RUN curl -fsSL https://get.docker.com -o get-docker.sh
RUN sh ./get-docker.sh

# Install SDKMAN
RUN curl -s "https://get.sdkman.io" -o getsdkman.sh
RUN bash ./getsdkman.sh

# Install JDK 17
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java $SDKMAN_JDK_VERSION"
# Install Gradle 7
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install gradle $SDKMAN_GRADLE_VERSION"
# Install Quarkus CLI
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install quarkus $SDKMAN_QUARKUS_VERSION"

# Install native compiler to enable quarkus native compilation

# Install Node 24
# RUN curl -fsSL https://deb.nodesource.com/setup_${NODE_MAJOR_VERSION}.x -o nodesource_setup.sh
# RUN bash nodesource_setup.sh
RUN curl https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.3/install.sh -o get_nvm.sh
RUN bash get_nvm.sh
RUN bash -c "source $HOME/.bashrc && nvm install 24 && nvm use 24 && nvm alias default 24"
RUN bash -c "source $HOME/.bashrc && corepack enable pnpm && corepack use pnpm@10"

workdir /workspace
RUN pnpm install
CMD 