FROM debian:trixie-slim

ENV SDKMAN_JDK_VERSION=21-tem
ENV SDKMAN_GRADLE_VERSION=8.14.3
ENV SDKMAN_QUARKUS_VERSION=3.26.1
ENV NODE_MAJOR_VERSION=24
ENV PNPM_VERSION=10
ENV NODE_OPTIONS='--max-old-space-size=200'
ENV GRADLE_OPTS="-Xmx512m"

workdir /install

RUN apt update
RUN apt install -y git 
RUN apt install -y curl
RUN apt install -y wget
RUN apt install -y unzip
RUN apt install -y zip
RUN apt install -y libxtst6
RUN apt install -y libxi6
RUN apt install -y zlib1g-dev
RUN apt install -y libgtk-3-0
RUN apt install -y libgbm-dev
RUN apt install -y libnotify-dev
RUN apt install -y libnss3
RUN apt install -y libxss1
RUN apt install -y libasound2
RUN apt install -y xauth
RUN apt install -y graphviz
RUN apt install -y xvfb
RUN apt install -y nodejs
RUN apt install -y gcc
RUN apt install -y chromium

## Install Docker CE Cli
# Add Docker's official GPG key:
RUN apt install -y ca-certificates
RUN install -m 0755 -d /etc/apt/keyrings
RUN curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc
RUN chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
RUN echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/debian \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  tee /etc/apt/sources.list.d/docker.list > /dev/null
RUN apt update

# CLI install
RUN apt install -y docker-ce-cli

# Install SDKMAN
RUN curl -s "https://get.sdkman.io" -o getsdkman.sh
RUN bash ./getsdkman.sh

# Install JDK 21
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java $SDKMAN_JDK_VERSION"
# Install Gradle 7
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install gradle $SDKMAN_GRADLE_VERSION"
# Install Quarkus CLI
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install quarkus $SDKMAN_QUARKUS_VERSION"

# Install Node 24
# Install PNPM
RUN curl https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.3/install.sh -o get_nvm.sh
RUN bash get_nvm.sh
RUN bash -c "source $HOME/.bashrc && nvm install 24" 
RUN bash -c "source $HOME/.bashrc && nvm use 24"
RUN bash -c "source $HOME/.bashrc && nvm alias default 24"
RUN bash -c "source $HOME/.bashrc && corepack enable pnpm "
RUN bash -c "source $HOME/.bashrc && corepack use pnpm@10"

WORKDIR /workspace
RUN bash -c "source $HOME/.bashrc && pnpm install"
RUN bash -c "source $HOME/.bashrc && pnpm --filter apps-store-front-e2e exec cypress install"
CMD 