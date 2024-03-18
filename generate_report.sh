#!/bin/bash

# Function to generate log filename with build number and timestamp
generate_log_filename() {
    local build_number="$1"
    echo "logs/build_fail_${build_number}.log"  # Updated filename pattern
}

# Main script starts here
build_number="$1"  # Jenkins build number passed as argument
log_file=$(generate_log_filename "$build_number")

# Ensure "logs" directory exists
mkdir -p logs

# Write the build failure log content to the file
echo "Build failed at: $(date)" >> "$log_file"
echo "Build console output:" >> "$log_file"
echo "======================" >> "$log_file"
cat "pipeline.log" >> "$log_file"

# Clear pipeline.log
> pipeline.log
