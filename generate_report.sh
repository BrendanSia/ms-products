#!/bin/bash

LOG_FILE="pipeline.log"

REPORT_DIR="reports"

mkdir -p "$REPORT_DIR"

REPORT_FILENAME="failure_report_$(date +"%Y%m%d_%H%M%S").xml"

REPORT_PATH="$REPORT_DIR/$REPORT_FILENAME"

if [ -f "$REPORT_PATH" ]; then
    echo "Previous report exists. Overwriting..."
    rm "$REPORT_PATH"
fi

# Generate XML content for the report
echo "<failureReport>" >> "$REPORT_PATH"
echo "    <timestamp>$(date +"%Y-%m-%d %H:%M:%S")</timestamp>" >> "$REPORT_PATH"
echo "    <uniqueIdentifier>$(uuidgen)</uniqueIdentifier>" >> "$REPORT_PATH"

# Parse the log file for errors
if [ -f "$LOG_FILE" ]; then
    echo "    <errorDetails><![CDATA[" >> "$REPORT_PATH"
    cat "$LOG_FILE" >> "$REPORT_PATH"
    echo "    ]]></errorDetails>" >> "$REPORT_PATH"
fi

echo "</failureReport>" >> "$REPORT_PATH"

echo "New report generated: $REPORT_PATH"
