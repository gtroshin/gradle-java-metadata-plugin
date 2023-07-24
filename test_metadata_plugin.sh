#!/bin/bash
set -e

# If this script is planned to run on minimal or server-focused Linux distributions,
# we may need to install the `tree` package manually using the system's package manager,
# e.g., `sudo apt install tree` on Ubuntu.

errors=()

# Helper function to check metadata file format
check_metadata_file_format() {
    metadata_file="$1"
    separator="$2"

    package_line="package=.*"
    file_line="fileName=.*"
    loc_line="linesOfCode=.*"

    if [ "${separator}" != "\n" ]; then
        package_line="${package_line}${separator}"
        file_line="${file_line}${separator}"
    fi

    if ! grep -q "${package_line}" "${metadata_file}"; then
        errors+=("${metadata_file} does not contain correct package information")
    fi
    if ! grep -q "${file_line}" "${metadata_file}"; then
        errors+=("${metadata_file} does not contain correct file information")
    fi
    if ! grep -q "${loc_line}" "${metadata_file}"; then
        errors+=("${metadata_file} does not contain correct linesOfCode information")
    fi
}

# Cleanup function
cleanup() {
    echo "Cleaning up..."
    ./gradlew clean
}

# Check for JAVA_HOME
if [ -z "$JAVA_HOME" ]; then
    echo "JAVA_HOME is not set. Please set the JAVA_HOME env var and try again."
    exit 1
fi

# Initial cleanup
cleanup

# Run the tasks and validate metadata
for separator in "\n" ";" ","
do
  echo "Running extractJavaMetadata with separator=${separator} ..."
  ./gradlew extractJavaMetadata -Pseparator="${separator}"

  echo "Checking metadata format..."
  for project_dir in $(find . -maxdepth 1 -type d -name 'project*' | grep -v "project10")
  do
    for metadata_file in $(find "${project_dir}/build/metadata" -name "*.metadata")
    do
      check_metadata_file_format "${metadata_file}" "${separator}"
    done
  done

  # Cleanup after checking metadata
  cleanup
done

# Build the projects and check JAR files
echo "Building projects..."
./gradlew build

echo "Checking JAR files..."
for project_dir in $(find . -maxdepth 1 -type d -name 'project*' | grep -v "project10")
do
    jar_file="${project_dir}/build/libs/$(basename ${project_dir})-1.0.jar"
    if unzip -l "${jar_file}" | grep -q ".metadata"; then
        echo "${jar_file} contains it's metadata"
    else
        errors+=("Metadata not found in ${jar_file}")
    fi
done

# Check if errors array is not empty
if [ ${#errors[@]} -ne 0 ]; then
    echo "Errors found during testing:"
    for error in "${errors[@]}"
    do
      echo "  - ${error}"
    done
    exit 1
fi

echo "Hurray! All tests passed!"
