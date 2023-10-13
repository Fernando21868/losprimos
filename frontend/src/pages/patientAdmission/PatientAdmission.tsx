import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button } from "../../@components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "../../@components/ui/dialog";
import { Input } from "../../@components/ui/input";
import Page from "./Page";
import { faPersonCirclePlus } from "@fortawesome/free-solid-svg-icons";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "../../@components/ui/form";
import { Toaster } from "../../@components/ui/toaster";
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
import { Checkbox } from "../../@components/ui/checkbox";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../../@components/ui/select";
import { MapContainer, Marker, Popup, TileLayer } from "react-leaflet";

("use client");
import "../../../node_modules/leaflet/dist/leaflet.css";
import marker from "../../../node_modules/leaflet/dist/images/marker-icon.png";
import { Loader2 } from "lucide-react";
import * as z from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { StateAuthSlice } from "../../types/interfaces";
import { RootState } from "../../store/store";
import { AnyAction, ThunkDispatch } from "@reduxjs/toolkit";
import { useEffect, useState } from "react";
import { toast } from "../../@components/ui/use-toast";
import { ToastAction } from "../../@components/ui/toast";
import { alreadyRegister } from "../../store/authSlice";
import { useAddress } from "../../hooks/useAddress";
import { useMapEvents } from "react-leaflet";
import { Icon } from "leaflet";
import { registerUser } from "../../hooks/useEmployee";

/**
 * Type to obtain addresses
 * @date 10/9/2023 - 2:36:48 AM
 *
 * @typedef {TDataAddress}
 */
type TDataAddress = {
  id: string;
  nombre: string;
};

/**
 * Page manage users
 * @date 10/9/2023 - 2:35:58 AM
 *
 * @returns {*}
 */
export const PatientAdmission = () => {
  /**
   * Loading to wait until user is registered. Dispatch actions to register. Get data from API of adresses
   */
  const { loading }: StateAuthSlice = useSelector(
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

  /**
   * Latitude and longitude for register form. Passwords visibility. States for API of addresses.
   */
  const [latitude, setLatitude] = useState<number | undefined>(undefined);
  const [longitude, setLongitude] = useState<number | undefined>(undefined);
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [provinces, setProvinces] = useState<null | Array<TDataAddress>>();
  const [departaments, setDepartaments] =
    useState<null | Array<TDataAddress>>();
  const [municipalities, setmunicipalities] =
    useState<null | Array<TDataAddress>>();

  /**
   * Icon for maps
   * @date 10/9/2023 - 2:37:16 AM
   *
   * @type {*}
   */
  const myIcon = new Icon({
    iconUrl: marker,
    iconSize: [32, 32],
  });

  /**
   * Obtain data from API of addresses. Making a call to API.
   */
  useEffect(() => {
    async function fetchProvinces() {
      const { provincias: provinces } = await getProvinces();
      setProvinces(provinces);
    }
    fetchProvinces();
  }, []);

  /**
   * Form to register a user with default values.
   * @date 10/9/2023 - 2:37:44 AM
   *
   * @type {*}
   */
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      firstName: "Ferarias33",
      lastName: "Ferarias33",
      enrollment: "39808901",
      dni: "39808901",
      email: "39808901@fi.unju.edu.ar",
      username: "Ferarias33",
      password: "Ferarias33",
      type: "administrator",
      confirmPassword: "Ferarias33",
      phoneNumber: "123123",
      birthday: "2023-08-11",
      address: {
        province: "Jujuy",
        department: "Dr. Manuel Belgrano",
        municipality: "Vinalito",
        address: "Adresss",
        latitude: undefined,
        longitude: undefined,
      },
    },
  });

  /**
   * Function to submit data and call endpoint to register a user.
   * @param values data bein sent to endpoint.
   */
  async function onSubmit(values: z.infer<typeof formSchema>) {
    if (latitude && longitude) {
      values.address!.latitude = latitude;
      values.address!.longitude = longitude;
    }

    const response = await dispatch(registerUser(values));

    if (response.type === "auth/register/rejected") {
      toast({
        variant: "destructive",
        title: "Algo ocurrio mal.",
        description: response.payload as string,
        action: <ToastAction altText="Cerrar">Intentar nuevamente</ToastAction>,
      });
    } else {
      dispatch(alreadyRegister());
      navigate("/confirmationEmail");
    }
  }

  /**
   * Function to show or hide password
   * @date 10/9/2023 - 2:33:47 AM
   */
  function handleShowPassword() {
    setShowPassword((prev) => !prev);
  }

  /**
   * Function to show or hide password
   * @date 10/9/2023 - 2:33:47 AM
   */
  function handleShowConfirmPassword() {
    setShowConfirmPassword((prev) => !prev);
  }

  /**
   * Function to obtain departments
   * @date 10/9/2023 - 2:33:42 AM
   *
   * @async
   * @param {string} value province to obtain departments
   * @returns {*}
   */
  async function handleChangePronvice(value: string) {
    const { departamentos: departaments } = await getDepartments(value);
    setDepartaments(departaments);
  }

  /**
   * Function to obtain municipalities
   * @date 10/9/2023 - 2:34:46 AM
   *
   * @async
   * @param {string} value province to obtain municipalities
   * @returns {*}
   */
  async function handleChangeProvinceGetMunicipalities(value: string) {
    const { municipios: municipalities } = await getMunicipalities(value);
    setmunicipalities(municipalities);
  }

  /**
   * Function to set latitude and longitude of map
   * @date 10/9/2023 - 2:35:09 AM
   *
   * @returns {*}
   */
  const LocationFinderDummy = () => {
    useMapEvents({
      click(e) {
        setLatitude(e.latlng.lat);
        setLongitude(e.latlng.lng);
      },
    });
    return null;
  };

  return (
    <div className="m-4">
      <h1 className="text-lg">Administracion de usuarios</h1>
      <Dialog>
        <DialogTrigger asChild>
          <Button variant="outline" className="my-4">
            <FontAwesomeIcon className="mr-2" icon={faPersonCirclePlus} />
            Agregar paciente
          </Button>
        </DialogTrigger>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>
              Formulario para crear usuarios empleados - SAME 107
            </DialogTitle>
            <DialogDescription>
              Los usuarios empleados que son creados deberan ingresar a su email
              y verificar el <b>correo electronico enviado</b> para que sean
              dados de alta al sistema.
              <b>
                <i>
                  Una vez que ingresen al sistema, deberan cambiar su contraseña
                  actual con una personal.
                </i>
              </b>
            </DialogDescription>
          </DialogHeader>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="w-full">
              <Toaster></Toaster>
              <Tabs defaultValue="account" className="max-w-md w-full">
                <TabsList className="flex flex-col h-full w-full">
                  <TabsTrigger
                    className="w-full hover:bg-accent-foreground hover:text-accent"
                    value="account"
                  >
                    Campos obligatorios
                  </TabsTrigger>
                  <TabsTrigger
                    className="w-full hover:bg-accent-foreground hover:text-accent"
                    value="password"
                  >
                    Campos opcionales
                  </TabsTrigger>
                </TabsList>
                <TabsContent value="account">
                  <Card>
                    <CardHeader>
                      <CardTitle>Campos obligatorios</CardTitle>
                      <CardDescription>
                        Los siguientes campos son todos obligatorios para crear
                        un usuario empleado.
                      </CardDescription>
                    </CardHeader>
                    <CardContent className="space-y-2">
                      <FormField
                        control={form.control}
                        name="firstName"
                        render={({ field }) => (
                          <FormItem className="mb-4">
                            <FormLabel>Nombres</FormLabel>
                            <FormControl>
                              <Input
                                placeholder="Por ejemplo: Lucas"
                                {...field}
                              />
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
                              <Input
                                placeholder="Por ejemplo: Rodriguez"
                                {...field}
                              />
                            </FormControl>
                            <FormMessage />
                          </FormItem>
                        )}
                      />
                      <FormField
                        control={form.control}
                        name="enrollment"
                        render={({ field }) => (
                          <FormItem className="mb-4">
                            <FormLabel>Matricula</FormLabel>
                            <FormControl>
                              <Input
                                placeholder="Por ejemplo: 42314"
                                {...field}
                              />
                            </FormControl>
                            <FormMessage />
                          </FormItem>
                        )}
                      />
                      <FormField
                        control={form.control}
                        name="dni"
                        render={({ field }) => (
                          <FormItem className="mb-4">
                            <FormLabel>DNI</FormLabel>
                            <FormControl>
                              <Input
                                placeholder="Por ejemplo: 37897892"
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
                              <Input
                                placeholder="Por ejemplo: Usuario"
                                {...field}
                              />
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
                              La contraseña debe cotener por lo menos 4
                              caracteres, 1 mayuscula, 1 minuscula, y 1 numero
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
                        <Checkbox
                          onClick={handleShowPassword}
                          id="showPassword"
                        />
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
                        name="type"
                        render={({ field }) => (
                          <FormItem className="mb-4">
                            <FormLabel>Tipo de usuario empleado</FormLabel>
                            <Select
                              onValueChange={field.onChange}
                              defaultValue={field.value}
                            >
                              <FormControl>
                                <SelectTrigger>
                                  <SelectValue placeholder="Selecione un usuario empleado" />
                                </SelectTrigger>
                              </FormControl>
                              <SelectContent className="h-[200px] overflow-auto z-50">
                                <SelectItem value="administrator">
                                  Administrador
                                </SelectItem>
                                <SelectItem value="doctor">Doctor</SelectItem>
                                <SelectItem value="nurse">Enfermero</SelectItem>
                                <SelectItem value="socialWorker">
                                  Trabajador social
                                </SelectItem>
                                <SelectItem value="kinesiologist">
                                  Kinesiologo
                                </SelectItem>
                                <SelectItem value="nutritionist">
                                  Nutricionista
                                </SelectItem>
                                <SelectItem value="psychologist">
                                  Psicologo
                                </SelectItem>
                              </SelectContent>
                            </Select>
                            <FormMessage />
                          </FormItem>
                        )}
                      />
                    </CardContent>
                    <CardFooter className="flex flex-col justify-start items-start">
                      {loading ? (
                        <Button className="mt-4" disabled>
                          <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                          Por favor espere
                        </Button>
                      ) : (
                        <Button className="mt-4" type="submit">
                          Registrar empleado
                        </Button>
                      )}
                    </CardFooter>
                  </Card>
                </TabsContent>
                <TabsContent value="password">
                  <Card>
                    <CardHeader>
                      <CardTitle>Campos opcionales</CardTitle>
                      <CardDescription>
                        Los siguientes campos son opcionales y podra cambiarlos
                        o llenarlos cuando desee.
                      </CardDescription>
                    </CardHeader>
                    <CardContent className="space-y-2">
                      <FormField
                        control={form.control}
                        name="phoneNumber"
                        render={({ field }) => (
                          <FormItem className="mb-4">
                            <FormLabel>Telefono o celular</FormLabel>
                            <FormControl>
                              <Input
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
                        name="address.address"
                        render={({ field }) => (
                          <FormItem className="mb-4">
                            <FormLabel>Direccion</FormLabel>
                            <FormControl>
                              <Input
                                placeholder="Por ejemplo: Jujuy 123"
                                {...field}
                              />
                            </FormControl>
                            <FormMessage />
                          </FormItem>
                        )}
                      />
                      <FormField
                        control={form.control}
                        name="address.province"
                        render={({ field }) => (
                          <FormItem className="mb-4">
                            <FormLabel>Provincia</FormLabel>
                            <Select
                              onValueChange={(value: string) => {
                                field.onChange(value);
                                handleChangePronvice(value);
                                handleChangeProvinceGetMunicipalities(value);
                              }}
                              defaultValue={field.value}
                              disabled={
                                loadingAddress || !provinces ? true : false
                              }
                            >
                              <FormControl>
                                <SelectTrigger>
                                  <SelectValue placeholder="Seleccione una provincia" />
                                </SelectTrigger>
                              </FormControl>
                              <SelectContent className="h-[200px] overflow-auto z-50">
                                {provinces?.map((province) => {
                                  return (
                                    <SelectItem
                                      key={province.id}
                                      value={province.nombre}
                                    >
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
                        name="address.department"
                        render={({ field }) => (
                          <FormItem className="mb-4">
                            <FormLabel>Departamento</FormLabel>
                            <Select
                              onValueChange={field.onChange}
                              defaultValue={field.value}
                              disabled={
                                loadingAddress || !departaments ? true : false
                              }
                            >
                              <FormControl>
                                <SelectTrigger>
                                  <SelectValue placeholder="Seleccione un departamento" />
                                </SelectTrigger>
                              </FormControl>
                              <SelectContent className="h-[200px] overflow-auto z-50">
                                {departaments?.map((departament) => {
                                  return (
                                    <SelectItem
                                      key={departament.id}
                                      value={departament.nombre}
                                    >
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
                        name="address.municipality"
                        render={({ field }) => (
                          <FormItem className="mb-4">
                            <FormLabel>Municipio</FormLabel>
                            <Select
                              onValueChange={field.onChange}
                              defaultValue={field.value}
                              disabled={
                                loadingAddress || !municipalities ? true : false
                              }
                            >
                              <FormControl>
                                <SelectTrigger>
                                  <SelectValue placeholder="Seleccione un municipio" />
                                </SelectTrigger>
                              </FormControl>
                              <SelectContent className="h-[200px] overflow-auto z-50">
                                {municipalities?.map((municipality) => {
                                  return (
                                    <SelectItem
                                      key={municipality.id}
                                      value={municipality.nombre}
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
                      <FormLabel>Domicilio</FormLabel>
                      <MapContainer
                        center={[-24.19457, -65.29712]}
                        zoom={13}
                        scrollWheelZoom={false}
                        style={{
                          height: "300px",
                          width: "100%",
                          zIndex: "10",
                        }}
                      >
                        <LocationFinderDummy />
                        <TileLayer
                          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        />
                        <Marker
                          position={[
                            latitude ?? -24.19457,
                            longitude ?? -65.29712,
                          ]}
                          icon={myIcon}
                        >
                          <Popup>
                            Domicilio en
                            <br />
                            Latitud: {latitude ?? -24.19457}
                            <br />
                            Longitud: {longitude ?? -65.29712}
                          </Popup>
                        </Marker>
                      </MapContainer>
                    </CardContent>
                    <CardFooter className="flex flex-col justify-start items-start">
                      {loading ? (
                        <Button className="mt-4" disabled>
                          <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                          Por favor espere
                        </Button>
                      ) : (
                        <Button className="mt-4" type="submit">
                          Registrar empleado
                        </Button>
                      )}
                    </CardFooter>
                  </Card>
                </TabsContent>
              </Tabs>
            </form>
          </Form>
        </DialogContent>
      </Dialog>
      <Page></Page>
    </div>
  );
};

const formSchema = z
  .object({
    firstName: z
      .string()
      .nonempty({
        message: "Debe ingresar un nombre de usuario",
      })
      .max(30, {
        message: "El nombre de usuario no debe superar los 30 caracteres",
      })
      .trim(),
    lastName: z
      .string()
      .nonempty({
        message: "Debe ingresar un nombre de usuario",
      })
      .max(30, {
        message: "El nombre de usuario no debe superar los 30 caracteres",
      })
      .trim(),
    enrollment: z
      .string()
      .nonempty({
        message: "Debe ingresar una matricula",
      })
      .max(30, {
        message: "La matricula no debe superar los 30 caracteres",
      })
      .trim(),
    dni: z
      .string()
      .nonempty({
        message: "Debe ingresar un DNI",
      })
      .max(30, {
        message: "El DNI no debe superar los 30 caracteres",
      })
      .trim(),
    username: z
      .string()
      .nonempty({
        message: "Debe ingresar un nombre de usuario",
      })
      .max(30, {
        message: "El nombre de usuario no debe superar los 30 caracteres",
      })
      .trim(),
    password: z
      .string()
      .nonempty({
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
      .nonempty({
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
    type: z
      .string()
      .nonempty({
        message: "Debe ingresar un tipo de usuario empleado",
      })
      .max(30, {
        message:
          "El tipo de usuario empleado no debe superar los 30 caracteres",
      })
      .trim(),
    email: z
      .string()
      .nonempty({
        message: "Debe ingresar un e-mail",
      })
      .max(100, {
        message: "El email no debe superar los 100 caracteres",
      })
      .email({
        message: "Ingrese un email valido",
      })
      .trim(),
    birthday: z.string().optional(),
    phoneNumber: z
      .string()
      .max(30, {
        message: "El numero de telefono no debe superar los 100 caracteres",
      })
      .trim()
      .optional(),
    address: z
      .object({
        address: z
          .string()
          .max(100, {
            message: "La direccion no debe superar los 100 caracteres",
          })
          .trim()
          .optional(),
        province: z
          .string()
          .max(100, {
            message: "La provincia no debe superar los 100 caracteres",
          })
          .trim()
          .optional(),
        department: z
          .string()
          .max(100, {
            message: "El departamento no debe superar los 100 caracteres",
          })
          .trim()
          .optional(),
        municipality: z
          .string()
          .max(100, {
            message: "La localidad no debe superar los 100 caracteres",
          })
          .trim()
          .optional(),
        latitude: z.number().optional(),
        longitude: z.number().optional(),
      })
      .optional(),
  })
  .superRefine(({ confirmPassword, password }, ctx) => {
    if (confirmPassword !== password) {
      ctx.addIssue({
        code: "custom",
        message: "Las contraseñas no coinciden",
      });
    }
  });
