package main

import(
	"encoding/json"
	"log"
	"net/http" //http client
	"github.com/gorilla/mux" //router lib
)

var message string

type Text struct{
	Message string `json:"message"`
}

func CreateMessage(w http.ResponseWriter, r *http.Request){
	name := r.FormValue("name")
	w.Header().Set("Content-Type", "application/json;charset=UTF-8")
	
	message := Text{Message: "Hello " + name + "!"}
	json.NewEncoder(w).Encode(message) //parse the message to JSON format
}

func main(){
	router := mux.NewRouter()
	router.Path("/say-hello").Queries("name", "{name}").HandlerFunc(CreateMessage).Methods("POST")
	log.Fatal(http.ListenAndServe(":8080", router))
}

