#!/bin/bash

LOG_DIR="reports"

mkdir -p "$LOG_DIR"

LOG_FILENAME="build_failure_log_$(date +"%Y%m%d_%H%M%S").log"

LOG_PATH="$LOG_DIR/$LOG_FILENAME"

# Check if a previous log file exists
if [ -f "$LOG_PATH" ]; then
    # If previous log file exists, overwrite it with a new one
    echo "Previous log file exists. Overwriting..."
    rm "$LOG_PATH"
fi

cat > "$LOG_PATH"

echo "Log file generated: $LOG_PATH"

