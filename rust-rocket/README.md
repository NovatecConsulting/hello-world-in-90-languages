# "Hello World" in Rust

Using the Rocket library, which seems to be the most popular *batteries included* web framework for Rust.

## How to install/run

* Install [cargo](https://doc.rust-lang.org/cargo/getting-started/installation.html)
* You have to use cargo nightly for rocket to work!
* `cargo run`

## How I install the nightly rust toolchain

There are many ways to install rustc/cargo, so here's the way I do it.
[rustup](https://rustup.rs/) is the officially recommended way to install the rust toolchain.

I wouldn't recommend doing `curl ... | sh` though, at least download the script and read it before executing it.
I use my package manager to install rustup.

```sh
sudo pacman -S rustup # install rustup. If you're not on Arch linux, get it somewhere else
rustup toolchain install nightly # installs the nightly version of rustc and cargo 

# You can build this project now. If you want to write rust, you may want to install rustfmt and download the sources:
rustup component add rustfmt # formatter for rust code
rustup component add rust-src # source code for the standard library.
```
