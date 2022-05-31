package gui.controller;

public class ActionManager{

    private PrettyAction prettyAction;
    private BulkImportAction bulkImportAction;
    private ExportAction exportAction;
    private RunAction runAction;

    public ActionManager() {
        initialiseActions();
    }

    private void initialiseActions() {

        prettyAction = new PrettyAction();
        bulkImportAction = new BulkImportAction();
        exportAction = new ExportAction();
        runAction = new RunAction();

    }

    public BulkImportAction getBulkImportAction() {
        return bulkImportAction;
    }

    public void setBulkImportAction(BulkImportAction bulkImportAction) {
        this.bulkImportAction = bulkImportAction;
    }

    public PrettyAction getPrettyAction() {
        return prettyAction;
    }

    public void setPrettyAction(PrettyAction prettyAction) {
        this.prettyAction = prettyAction;
    }

    public ExportAction getExportAction() {
        return exportAction;
    }

    public void setExportAction(ExportAction exportAction) {
        this.exportAction = exportAction;
    }

    public RunAction getRunAction() {
        return runAction;
    }

    public void setRunAction(RunAction runAction) {
        this.runAction = runAction;
    }
}
