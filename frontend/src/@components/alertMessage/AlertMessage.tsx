import { AlertCircle } from "lucide-react";
import { Alert, AlertDescription, AlertTitle } from "../ui/alert";
import { Button } from "../ui/button";

type Props = {
  title: string;
  description: string;
  variant?: "default" | "destructive";
  buttonText: string;
  setCloseAlertMessage: (close: boolean) => void;
};

export default function AlertMessage({
  title,
  description,
  variant = "default",
  buttonText,
  setCloseAlertMessage,
}: Props) {
  return (
    <Alert variant={variant} className="m-4 max-w-md">
      <AlertCircle className="h-4 w-4" />
      <div className="flex justify-center items-center gap-4">
        <div>
          <AlertTitle>{title}</AlertTitle>
          <AlertDescription>{description}</AlertDescription>
        </div>
        <Button onClick={() => setCloseAlertMessage(false)} variant={variant}>
          {buttonText}
        </Button>
      </div>
    </Alert>
  );
}
