package eu.darken.sdmse.systemcleaner.core.tasks

import android.text.format.Formatter
import eu.darken.sdmse.R
import eu.darken.sdmse.common.ca.caString
import eu.darken.sdmse.common.getQuantityString2
import eu.darken.sdmse.main.core.SDMTool
import eu.darken.sdmse.stats.core.HasReportDetails
import eu.darken.sdmse.stats.core.Reportable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SystemCleanerSchedulerTask(
    val schedulerId: String,
) : SystemCleanerTask, Reportable {

    sealed interface Result : SystemCleanerTask.Result {
        override val type: SDMTool.Type get() = SDMTool.Type.SYSTEMCLEANER
    }

    @Parcelize
    data class Success(
        private val processedItems: Int,
        private val recoveredSpace: Long,
    ) : Result, HasReportDetails {
        override val primaryInfo
            get() = caString {
                getQuantityString2(R.plurals.systemcleaner_result_x_items_deleted, processedItems)
            }

        override val secondaryInfo
            get() = caString {
                getString(
                    eu.darken.sdmse.common.R.string.general_result_x_space_freed,
                    Formatter.formatFileSize(this, recoveredSpace)
                )
            }
    }
}