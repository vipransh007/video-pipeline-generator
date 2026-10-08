# Start with a lightweight Ubuntu base
FROM ubuntu:22.04

# Install curl so we can download the Kubernetes tool
RUN apt-get update && apt-get install -y curl

# Download the latest stable release of kubectl
RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"

# Make the file executable and move it to your system PATH
RUN chmod +x kubectl && mv kubectl /usr/local/bin/

# When the container runs, execute this command to verify the installation
CMD ["kubectl", "version", "--client"]