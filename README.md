# customer_envoy

![alt text](https://github.com/robertcamilo23/customer_envoy/blob/master/images/envoyWholeProcessWithgRPC.png)

# To run this sample

Please clone this repository, and then:

1. Change the following IP address: **192.168.6.165** by your local IP address in the **envoy-customer-client.json** and **envoy-smart-proxy-config.json** files, that are located on the config folder.

2. Run the command: **docker-compose -f docker-compose-smart-proxy.yml up --build**

3. Make a request to: **localhost:8181/tproxy/validate** using a JSON payload like in the following image:

![alt text](https://github.com/robertcamilo23/customer_envoy/blob/master/images/postmanRequestHttp1.png)
