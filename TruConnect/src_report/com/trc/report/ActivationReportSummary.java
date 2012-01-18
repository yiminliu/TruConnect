package com.trc.report;

import java.text.DecimalFormat;

public class ActivationReportSummary {
  private static DecimalFormat twoPlaces = new DecimalFormat("#.##");
  private int activated;
  private int uniqueReservations;
  private int failedReservations;
  private int successfulReservations;

  public int getTotalUsers() {
    return (uniqueReservations - successfulReservations) + activated;
  }

  public double getPercentageSuccess() {
    return Double.valueOf(twoPlaces.format((activated) / (0.0 + getTotalUsers())));
  }

  public double getPercentageFailure() {
    return Double.valueOf(twoPlaces.format((uniqueReservations - successfulReservations) / (0.0 + getTotalUsers())));
  }

  public double getPercentageDifficulty() {
    return Double.valueOf(twoPlaces.format((successfulReservations) / (0.0 + getTotalUsers())));
  }

  public int getActivated() {
    return activated;
  }

  public void setActivated(int activated) {
    this.activated = activated;
  }

  public int getUniqueReservations() {
    return uniqueReservations;
  }

  public void setUniqueReservations(int uniqueReservations) {
    this.uniqueReservations = uniqueReservations;
  }

  public int getFailedReservations() {
    return failedReservations;
  }

  public void setFailedReservations(int failedReservations) {
    this.failedReservations = failedReservations;
  }

  public int getSuccessfulReservations() {
    return successfulReservations;
  }

  public void setSuccessfulReservations(int successfulReservations) {
    this.successfulReservations = successfulReservations;
  }

  public int getNumUniqueUsers() {
    return getActivated() + getFailedReservations();
  }

}
