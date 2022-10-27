package com.task.digital.data

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PrinterInfoTest {

    @Test
    fun `compare two printers are equal returns true`() {
        val printer1 = PrinterInfo("HP", "T1000", PrinterConnectivityStatus.ONLINE)
        val printer2 = PrinterInfo("HP", "T1000", PrinterConnectivityStatus.ONLINE)
        val result = printer1 == printer2
        assertThat(result).isTrue()
    }

    @Test
    fun `compare two printers with different connectivity are equal returns true`() {
        val printer1 = PrinterInfo("HP", "T1000", PrinterConnectivityStatus.ONLINE)
        val printer2 = PrinterInfo("HP", "T1000", PrinterConnectivityStatus.OFFLINE)
        val result = printer1 == printer2
        assertThat(result).isTrue()
    }

    @Test
    fun `compare two printers with different file queues are equal returns true`() {
        val printer1 = PrinterInfo("HP", "T1000", PrinterConnectivityStatus.ONLINE)
        printer1.fileQueue.add(ItemInfo("file1", "pdf", JobStatus.WAITING, 1000))
        val printer2 = PrinterInfo("HP", "T1000", PrinterConnectivityStatus.ONLINE)
        printer2.fileQueue.add(ItemInfo("file2", "png", JobStatus.IN_PROGRESS, 30000))
        val result = printer1 == printer2
        assertThat(result).isTrue()
    }

    @Test
    fun `compare two printers with different names and models are equal returns false`() {
        val printer1 = PrinterInfo("Samsung", "MFP500", PrinterConnectivityStatus.ONLINE)
        val printer2 = PrinterInfo("HP", "T1000", PrinterConnectivityStatus.ONLINE)
        val result = printer1 == printer2
        assertThat(result).isFalse()
    }

    @Test
    fun `check if printer with online connectivity is online returns true`() {
        val printer = PrinterInfo("HP", "T1000", PrinterConnectivityStatus.ONLINE)
        val result = printer.isOnline()
        assertThat(result).isTrue()
    }

    @Test
    fun `check if printer with offline connectivity is online returns false`() {
        val printer = PrinterInfo("HP", "T1000", PrinterConnectivityStatus.OFFLINE)
        val result = printer.isOnline()
        assertThat(result).isFalse()
    }

    @Test
    fun `check if printer toString() works correctly returns true`() {
        val printer = PrinterInfo("HP", "T1000", PrinterConnectivityStatus.OFFLINE)
        val result = "HP T1000 Offline" == printer.toString()
        assertThat(result).isTrue()
    }
}