"use client";
import "../../../node_modules/leaflet/dist/leaflet.css";
import marker from "../../../node_modules/leaflet/dist/images/marker-icon.png";
import { Loader2 } from "lucide-react";
import * as z from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { Button } from "../../@components/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "../../@components/ui/form";
import { Input } from "../../@components/ui/input";
import { Link, useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { StateAuthSlice } from "../../types/interfaces";
import { RootState } from "../../store/store";
import { registerUser } from "../../data/authActions";
import { AnyAction, ThunkDispatch } from "@reduxjs/toolkit";
import { useEffect, useState } from "react";
import { Checkbox } from "../../@components/ui/checkbox";
import { toast } from "../../@components/ui/use-toast";
import { ToastAction } from "../../@components/ui/toast";
import AlertMessage from "../../@components/alertMessage/AlertMessage";
import { alreadyRegister } from "../../store/authSlice";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../../@components/ui/select";
import { useAddress } from "../../hooks/useAddress";
import { MapContainer, Marker, Popup, TileLayer, useMap } from "react-leaflet";
import { Icon } from "leaflet";
import {
  Tabs,
  TabsContent,
  TabsList,
  TabsTrigger,
} from "../../@components/ui/tabs";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "../../@components/ui/card";

type TDataAddress = {
  id: string;
  nombre: string;
};

function Register() {
  const { loading, userInfo, success, error }: StateAuthSlice = useSelector(
    (state: RootState) => state["auth"]
  );
  const dispatch = useDispatch<ThunkDispatch<RootState, any, AnyAction>>();
  const navigate = useNavigate();
  const {
    getProvinces,
    getDepartments,
    getMunicipalities,
    loading: loadingAddress,
  } = useAddress();

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [closeAlertMessage, setCloseAlertMessage] = useState(false);
  const [provinces, setProvinces] = useState<null | Array<TDataAddress>>();
  const [departaments, setDepartaments] =
    useState<null | Array<TDataAddress>>();
  const [municipalities, setmunicipalities] =
    useState<null | Array<TDataAddress>>();

  const myIcon = new Icon({
    iconUrl: marker,
    iconSize: [32, 32],
  });

  useEffect(() => {
    if (userInfo) {
      navigate("/");
    }
    if (success) navigate("/login");

    async function fetchProvinces() {
      const { provincias: provinces } = await getProvinces();
      setProvinces(provinces);
    }
    fetchProvinces();
  }, [navigate, userInfo, success]);

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      confirmPassword: "",
      phoneNumber: "",
      address: "",
      birthday: "",
    },
  });

  async function onSubmit(values: z.infer<typeof formSchema>) {
    const response = await dispatch(registerUser(values));
    if (error) {
      setCloseAlertMessage(true);
    } else {
      setCloseAlertMessage(false);
    }
    dispatch(alreadyRegister());
    navigate("/confirmationEmail");
  }

  function handleShowPassword() {
    setShowPassword(!showPassword);
  }

  function handleShowConfirmPassword() {
    setShowConfirmPassword(!showConfirmPassword);
  }

  async function handleChangePronvice(value: string) {
    const { departamentos: departaments } = await getDepartments(value);
    setDepartaments(departaments);
  }

  async function handleChangeProvinceGetMunicipalities(value: string) {
    const { municipios: municipalities } = await getMunicipalities(value);
    setmunicipalities(municipalities);
  }

  return (
    <div className="p-4 flex flex-col items-center justify-center">
      {error && closeAlertMessage ? (
        <AlertMessage
          description={error}
          title="Error"
          variant="destructive"
          buttonText="Cerrar"
          setCloseAlertMessage={setCloseAlertMessage}
        ></AlertMessage>
      ) : (
        ""
      )}
      <Tabs defaultValue="account" className="max-w-md w-full">
        <TabsList className="grid w-full grid-cols-2">
          <TabsTrigger value="account">Campos obligatorios</TabsTrigger>
          <TabsTrigger value="password">Campos opcionales</TabsTrigger>
        </TabsList>
        <TabsContent value="account">
          <Card>
            <CardHeader>
              <CardTitle>Campos obligatorios</CardTitle>
              <CardDescription>
                Los siguientes campos son opcionales y podra cambiarlos o
                llenarlos caundo desee.
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-2"></CardContent>
            <CardFooter>
              <Button>Save changes</Button>
            </CardFooter>
          </Card>
        </TabsContent>
        <TabsContent value="password">
          <Card>
            <CardHeader>
              <CardTitle>Campos opcionales</CardTitle>
              <CardDescription>
                Los siguientes campos son todos obligatorios para crear una
                cuenta.
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-2"></CardContent>
            <CardFooter>
              <Button>Save password</Button>
            </CardFooter>
          </Card>
        </TabsContent>
      </Tabs>
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="max-w-md w-full"
        >
          <FormField
            control={form.control}
            name="firstName"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Nombres</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: Lucas" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="lastName"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Apellidos</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: Rodriguez" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="email"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>E-mail</FormLabel>
                <FormControl>
                  <Input
                    type="email"
                    placeholder="Por ejemplo: lucasRodriguez12@gmail.com"
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="username"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Usuario</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: Usuario" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="password"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Contraseña</FormLabel>
                <FormDescription>
                  La contraseña debe cotener por lo menos 4 caracteres, 1
                  mayuscula, 1 minuscula, y 1 numero
                </FormDescription>
                <FormControl>
                  <Input
                    type={showPassword ? "text" : "password"}
                    placeholder="Por ejemplo: Lucas123"
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <div className="-mt-2 mb-4 flex items-center space-x-2">
            <Checkbox onClick={handleShowPassword} id="showPassword" />
            <label
              htmlFor="showPassword"
              className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
            >
              Mostrar/ocultar contraseña
            </label>
          </div>
          <FormField
            control={form.control}
            name="confirmPassword"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Confirmar contraseña</FormLabel>
                <FormControl>
                  <Input
                    type={showConfirmPassword ? "text" : "password"}
                    placeholder="Por ejemplo: Lucas123"
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <div className="-mt-2 mb-4 flex items-center space-x-2">
            <Checkbox
              onClick={handleShowConfirmPassword}
              id="showConfirmPassowd"
            />
            <label
              htmlFor="showConfirmPassowd"
              className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
            >
              Mostrar/ocultar contraseña
            </label>
          </div>
          <FormField
            control={form.control}
            name="phoneNumber"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Telefono o celular</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: 3886905902" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="birthday"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Fecha de nacimiento</FormLabel>
                <FormControl>
                  <Input
                    type="date"
                    placeholder="Por ejemplo: 3886905902"
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="address"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Direccion</FormLabel>
                <FormControl>
                  <Input placeholder="Por ejemplo: Jujuy 123" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="province"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Provincia</FormLabel>
                <Select
                  onValueChange={(value: string) => {
                    field.onChange;
                    handleChangePronvice(value);
                    handleChangeProvinceGetMunicipalities(value);
                  }}
                  defaultValue={field.value}
                  disabled={loadingAddress || !provinces ? true : false}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Seleccione una provincia" />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent className="h-[200px] overflow-auto">
                    {provinces?.map((province) => {
                      return (
                        <SelectItem key={province.id} value={province.id}>
                          {province.nombre}
                        </SelectItem>
                      );
                    })}
                  </SelectContent>
                </Select>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="department"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Departamento</FormLabel>
                <Select
                  onValueChange={field.onChange}
                  defaultValue={field.value}
                  disabled={loadingAddress || !departaments ? true : false}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Seleccione un departamento" />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent className="h-[200px] overflow-auto">
                    {departaments?.map((departament) => {
                      return (
                        <SelectItem key={departament.id} value={departament.id}>
                          {departament.nombre}
                        </SelectItem>
                      );
                    })}
                  </SelectContent>
                </Select>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="locality"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Municipio</FormLabel>
                <Select
                  onValueChange={field.onChange}
                  defaultValue={field.value}
                  disabled={loadingAddress || !municipalities ? true : false}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Seleccione un municipio" />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent className="h-[200px] overflow-auto">
                    {municipalities?.map((municipality) => {
                      return (
                        <SelectItem
                          key={municipality.id}
                          value={municipality.id}
                        >
                          {municipality.nombre}
                        </SelectItem>
                      );
                    })}
                  </SelectContent>
                </Select>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="location"
            render={({ field }) => (
              <FormItem className="mb-4">
                <FormLabel>Domicilio</FormLabel>
                <MapContainer
                  center={[51.505, -0.09]}
                  zoom={13}
                  scrollWheelZoom={false}
                  style={{ height: "300px", width: "100%" }}
                >
                  <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                  />
                  <Marker position={[51.505, -0.09]} icon={myIcon}>
                    <Popup>
                      A pretty CSS3 popup. <br /> Easily customizable.
                    </Popup>
                  </Marker>
                </MapContainer>
                <FormMessage />
              </FormItem>
            )}
          />
          {loading ? (
            <Button className="mt-4" disabled>
              <Loader2 className="mr-2 h-4 w-4 animate-spin" />
              Por favor espere
            </Button>
          ) : (
            <Button
              className="mt-4"
              type="submit"
              onClick={() => {
                error
                  ? toast({
                      variant: "destructive",
                      title: "Algo salio mal.",
                      description: error,
                      action: (
                        <ToastAction altText="Try again">Try again</ToastAction>
                      ),
                    })
                  : undefined;
              }}
            >
              Registrarse
            </Button>
          )}
          <div>
            ¿Tienes una cuenta?
            <Button className="-ml-2" variant="link">
              <Link to={"/login"}>Inicia sesion</Link>
            </Button>
          </div>
        </form>
      </Form>
    </div>
  );
}

const formSchema = z
  .object({
    firstName: z
      .string()
      .min(1, {
        message: "Debe ingresar un nombre de usuario",
      })
      .max(30, {
        message: "El nombre de usuario no debe superar los 30 caracteres",
      })
      .trim(),
    lastName: z
      .string()
      .min(1, {
        message: "Debe ingresar un nombre de usuario",
      })
      .max(30, {
        message: "El nombre de usuario no debe superar los 30 caracteres",
      })
      .trim(),
    username: z
      .string()
      .min(1, {
        message: "Debe ingresar un nombre de usuario",
      })
      .max(30, {
        message: "El nombre de usuario no debe superar los 30 caracteres",
      })
      .trim(),
    password: z
      .string()
      .min(1, {
        message: "Debe ingresar una contraseña",
      })
      .max(30, {
        message: "La contraseña no debe superar los 30 caracteres",
      })
      .regex(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{4,}$/, {
        message:
          "La contraseña debe cotener por lo menos 4 caracteres, 1 mayuscula, 1 minuscula, y 1 numero",
      })
      .trim(),
    confirmPassword: z
      .string()
      .min(1, {
        message: "Debe ingresar una contraseña",
      })
      .max(30, {
        message: "La contraseña no debe superar los 30 caracteres",
      })
      .regex(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{4,}$/, {
        message:
          "La contraseña debe cotener por lo menos 4 caracteres, 1 mayuscula, 1 minuscula, y 1 numero",
      })
      .trim(),
    email: z
      .string()
      .min(1, {
        message: "Debe ingresar un e-mail",
      })
      .max(100, {
        message: "El email no debe superar los 100 caracteres",
      })
      .email({
        message: "Ingrese un email valido",
      })
      .trim(),
    birthday: z.string().trim().optional(),
    phoneNumber: z.string().trim().optional(),
    address: z.string().trim().optional(),
    province: z.string().trim().optional(),
    department: z.string().trim().optional(),
    locality: z.string().trim().optional(),
    location: z.string().trim().optional(),
  })
  .superRefine(({ confirmPassword, password }, ctx) => {
    if (confirmPassword !== password) {
      ctx.addIssue({
        code: "custom",
        message: "The passwords did not match",
      });
    }
  });

export default Register;
