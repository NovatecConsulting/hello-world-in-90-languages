#![feature(proc_macro_hygiene, decl_macro)]

#[macro_use] extern crate rocket;
use serde::Serialize;
use rocket::config::{Config, Environment};
use rocket_contrib::json::Json;

#[derive(Serialize)]
struct Message {
    message: String
}

fn msg(name: String) -> Json<Message> {
    Json(Message{message : format!("Hello, {}!", name)})
}

#[post("/say-hello?<name>")]
fn hello_name(name: String) -> Json<Message> {
    msg(name)
}

#[post("/say-hello")]
fn hello() -> Json<Message> {
    msg("World".to_string())
}

fn main() {
    // Disables emoji programmatically. Apparently the only way /shrug
    std::env::set_var("ROCKET_CLI_COLORS", "off");

    let conf = Config::build(Environment::Development)
        .port(8080)
        .finalize()
        .unwrap();
    rocket::custom(conf)
        .mount("/", routes![hello, hello_name])
        .launch();
}
