#!/bin/bash

LOG_DIR="reports"
mkdir -p "$LOG_DIR"

# Define a function to generate log file name with datetime
generate_log_filename() {
    echo "build_failure_log_$(date +"%Y%m%d_%H%M%S").log"
}

# Get the log filename
LOG_FILENAME=$(generate_log_filename)
LOG_PATH="$LOG_DIR/$LOG_FILENAME"

# Copy the pipeline log to the reports folder with a timestamped filename
cp pipeline.log "$LOG_PATH"

echo "Log file generated: $LOG_PATH"