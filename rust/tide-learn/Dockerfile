FROM rust:1.43.0 AS build
WORKDIR /usr/src/
RUN apt update && apt-get install -y musl-tools && rm -rf /var/lib/apt/lists/*
RUN rustup target add x86_64-unknown-linux-musl

RUN USER=root cargo new --lib tide-learn
WORKDIR /usr/src/tide-learn
COPY Cargo.toml Cargo.lock ./
RUN cargo build --release

COPY src ./src
RUN cargo install --target x86_64-unknown-linux-musl --path .

FROM scratch
WORKDIR /app/
COPY --from=build /usr/local/cargo/bin/hello /app/
EXPOSE 8080
USER 1000
ENTRYPOINT ["./hello"]