BAZEL := bazel
CLIENT := //client-side:client
EVENTMICROSERVICE := //server-side/EventMicroservice:event_microservice
TOKENGENERATORMICROSERVICE := //server-side/TokenGeneratorMicroservice:token_generator_microservice

all: build

build:
	$(BAZEL) build $(CLIENT) $(EVENTMICROSERVICE) $(TOKENGENERATORMICROSERVICE)

build_client:
	$(BAZEL) build $(CLIENT)

build_event_microservice:
	$(BAZEL) build $(EVENTMICROSERVICE)

build_token_generator_microservice:
	$(BAZEL) build $(TOKENGENERATORMICROSERVICE)

run_client:
	$(BAZEL) run $(CLIENT)

run_event_microservice:
	$(BAZEL) run $(EVENTMICROSERVICE)

run_token_generator_microservice:
	$(BAZEL) run $(TOKENGENERATORMICROSERVICE)

deps_client:
	$(BAZEL) query --notool_deps --nohost_deps --noimplicit_deps "deps($(CLIENT))" --output graph

deps_event_microservice:
	$(BAZEL) query --notool_deps --nohost_deps --noimplicit_deps "deps($(EVENTMICROSERVICE))" --output graph

deps_token_generator_microservice:
	$(BAZEL) query --notool_deps --nohost_deps --noimplicit_deps "deps($(TOKENGENERATORMICROSERVICE))" --output graph

clean:
	cd client-side && $(BAZEL) clean
	cd server-side/EventMicroservice && $(BAZEL) clean
	cd server-side/TokenGeneratorMicroservice && $(BAZEL) clean

.PHONY: all build run clean

