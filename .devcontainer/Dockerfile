FROM debian:trixie-slim

ENV SDKMAN_JDK_VERSION=21.0.8-tem
ENV SDKMAN_GRADLE_VERSION=8.14.3
ENV SDKMAN_QUARKUS_VERSION=3.26.1
ENV NODE_MAJOR_VERSION=24
ENV PNPM_VERSION=10.11.0

workdir /install

RUN apt update
RUN apt install -y git curl wget unzip zip libxtst6 graphviz libxi6 nodejs gcc zlib1g-dev

# Install SDKMAN
RUN curl -s "https://get.sdkman.io" | bash

# Install JDK 17
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java $SDKMAN_JDK_VERSION"
# Install Gradle 7
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install gradle $SDKMAN_GRADLE_VERSION"
# Install Quarkus CLI
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install quarkus $SDKMAN_QUARKUS_VERSION"

# Install native compiler to enable quarkus native compilation

# Install Node 24
RUN curl -fsSL https://deb.nodesource.com/setup_${NODE_MAJOR_VERSION}.x -o nodesource_setup.sh
RUN bash nodesource_setup.sh

# Install PNPM
RUN corepack enable pnpm
RUN corepack use pnpm@$PNPM_VERSION

## Install Docker CE Cli
RUN curl -fsSL https://get.docker.com -o get-docker.sh
RUN sh ./get-docker.sh

# Install requirements for contextmapper

workdir /workspace