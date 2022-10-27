package com.task.digital

import com.google.common.truth.Truth.assertThat
import com.task.digital.data.PrinterConnectivityStatus
import com.task.digital.data.PrinterInfo
import org.junit.Test

class MainViewModelTest {
    private val localViewModel = MainViewModel()

    @Test
    fun `add printer and check if it is in the printers list`() {
        val printer = PrinterInfo("HP", "T1000", PrinterConnectivityStatus.ONLINE)
        localViewModel.addPrinter(printer)
        val result = printer == localViewModel.printers[0]
        assertThat(result).isTrue()
    }

    @Test
    fun `check that a printer can't be added two times`() {
        val printer = PrinterInfo("HP", "T1000", PrinterConnectivityStatus.ONLINE)
        localViewModel.addPrinter(printer)
        localViewModel.addPrinter(printer)
        val result = localViewModel.printers.size == 1
        assertThat(result).isTrue()
    }

    @Test
    fun `check that the current printer is at the expected position in the printers list`() {
        val printer1 = PrinterInfo("HP", "T1000", PrinterConnectivityStatus.ONLINE)
        val printer2 = PrinterInfo("Samsung", "MFP500", PrinterConnectivityStatus.ONLINE)
        val printer3 = PrinterInfo("Cannon", "G2000", PrinterConnectivityStatus.OFFLINE)
        localViewModel.addPrinter(printer1)
        localViewModel.addPrinter(printer2)
        localViewModel.addPrinter(printer3)
        localViewModel.currentPrinterChoice = 2
        val result = localViewModel.getCurrentPrinter() != localViewModel.printers[0] &&
                localViewModel.getCurrentPrinter() != localViewModel.printers[1] &&
                localViewModel.getCurrentPrinter() == localViewModel.printers[2]
        assertThat(result).isTrue()
    }
}