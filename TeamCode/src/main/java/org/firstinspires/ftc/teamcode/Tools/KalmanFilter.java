package org.firstinspires.ftc.teamcode.Tools;

public class KalmanFilter {

    private double estimate; // The estimated value
    private double errorEstimate; // The estimated error
    private double errorMeasure; // The measurement noise
    private double gain; // The Kalman gain

    public KalmanFilter() {
        // Initialize with default values
        this.estimate = 0;
        this.errorEstimate = 1;
        this.errorMeasure = 1;
        this.gain = 0;
    }

    // Update the Kalman filter with a new measurement
    public double update(double measurement) {
        // Calculate Kalman gain
        gain = errorEstimate / (errorEstimate + errorMeasure);

        // Update the estimate with the new measurement
        estimate = estimate + gain * (measurement - estimate);

        // Update the error estimate
        errorEstimate = (1 - gain) * errorEstimate;

        return estimate;
    }

    // Set the measurement noise (variance)
    public void setMeasurementNoise(double errorMeasure) {
        this.errorMeasure = errorMeasure;
    }

    // Set the initial estimate and error estimate
    public void setInitialEstimate(double estimate, double errorEstimate) {
        this.estimate = estimate;
        this.errorEstimate = errorEstimate;
    }

    // Set the process noise (variance)
    public void setProcessNoise(double processNoise) {
        this.errorEstimate = processNoise;
    }

    // Get the current estimate
    public double getEstimate() {
        return estimate;
    }
}
