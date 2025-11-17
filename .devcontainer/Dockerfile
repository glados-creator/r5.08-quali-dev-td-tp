FROM debian:trixie-slim


RUN apt update
RUN apt install -y git curl wget unzip zip libxtst6 graphviz libxi6 nodejs gcc zlib1g-dev

workdir /install
## Install Docker CE Cli
RUN curl -fsSL https://get.docker.com -o get-docker.sh
RUN sh ./get-docker.sh

# Install SDKMAN
RUN curl -s "https://get.sdkman.io" -o getsdkman.sh
RUN bash ./getsdkman.sh

ENV SDKMAN_JDK_VERSION=25-tem
ENV SDKMAN_GRADLE_VERSION=
ENV SDKMAN_QUARKUS_VERSION=
ENV NODE_MAJOR_VERSION=24
ENV PNPM_VERSION=10

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

# Install PNPM
# RUN corepack enable pnpm
# RUN corepack use pnpm@$PNPM_VERSION

# Install requirements for contextmapper

workdir /workspace